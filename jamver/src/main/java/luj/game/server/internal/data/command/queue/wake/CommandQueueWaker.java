package luj.game.server.internal.data.command.queue.wake;

import java.util.List;
import java.util.Queue;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.wake.behaviors.WakeBehaviorFactory;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.execute.load.request.MissingLoadRequestor;

public class CommandQueueWaker {

  public CommandQueueWaker(Queue<DataCommandRequest> commandQueue, CacheContainer dataCache,
      ActorMessageHandler.Ref dataRef, ActorPreStartHandler.Actor saveRef,
      ActorPreStartHandler.Actor loadRef, BeanContext lujbean) {
    _commandQueue = commandQueue;
    _dataCache = dataCache;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _loadRef = loadRef;
    _lujbean = lujbean;
  }

  public void wake() {
    _commandQueue.removeIf(this::tryExecute);
  }

  private boolean tryExecute(DataCommandRequest commandReq) {
    QueueWakeBehavior behavior = new WakeBehaviorFactory(
        commandReq, _dataCache, _dataRef, _saveRef, _lujbean).create();

    List<CacheRequest> cacheReq = behavior.getCacheReq();
    DataReadyChecker.Result readyResult = new DataReadyChecker(cacheReq, _dataCache).check();

    if (!readyResult.isReady()) {
      new MissingLoadRequestor(readyResult.getMissingList(), _dataCache, _loadRef).request();
      return false;
    }

    //TODO: 后面要做成 在此锁定数据后，发往execActor执行
    behavior.finishExec();

    return true;
  }

  private final Queue<DataCommandRequest> _commandQueue;
  private final CacheContainer _dataCache;

  private final ActorMessageHandler.Ref _dataRef;
  private final ActorPreStartHandler.Actor _saveRef;
  private final ActorPreStartHandler.Actor _loadRef;

  private final BeanContext _lujbean;
}
