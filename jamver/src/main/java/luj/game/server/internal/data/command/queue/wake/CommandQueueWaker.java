package luj.game.server.internal.data.command.queue.wake;

import java.util.List;
import java.util.Queue;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.wake.behaviors.WakeBehaviorFactory;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.execute.load.request.MissingLoadRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandQueueWaker {

  public CommandQueueWaker(Queue<DataCommandRequest> commandQueue, CacheContainer dataCache,
      Tellable dataRef, Tellable saveRef, Tellable loadRef, BeanContext lujbean) {
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
      MissingLoadRequestor.create(readyResult.getMissingList(), _dataCache, _loadRef).request();
      return false;
    }

    //TODO: 后面要做成 在此锁定数据后，发往execActor执行
    try {
      behavior.finishExec();
    } catch (RuntimeException e) {
      LOG.error(e.getMessage(), e);
    }

    return true;
  }

  private static final Logger LOG = LoggerFactory.getLogger(CommandQueueWaker.class);

  private final Queue<DataCommandRequest> _commandQueue;
  private final CacheContainer _dataCache;

  private final Tellable _dataRef;
  private final Tellable _saveRef;
  private final Tellable _loadRef;

  private final BeanContext _lujbean;
}
