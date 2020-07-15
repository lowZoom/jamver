package luj.game.server.internal.data.command.queue.wake;

import java.util.Queue;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.execute.finish.CommandExecFinisher;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.execute.load.request.MissingLoadRequestor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class CommandQueueWaker {

  public CommandQueueWaker(Queue<DataCommandRequest> commandQueue, CacheContainer dataCache,
      ActorMessageHandler.Ref dataRef, ActorPreStartHandler.Actor saveRef,
      ActorPreStartHandler.Actor loadRef) {
    _commandQueue = commandQueue;
    _dataCache = dataCache;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _loadRef = loadRef;
  }

  public void wake() {
    _commandQueue.removeIf(this::tryExecute);
  }

  private boolean tryExecute(DataCommandRequest commandReq) {
    CacheRequest cacheReq = commandReq.getCacheReq();
    DataReadyChecker.Result readyResult = new DataReadyChecker(cacheReq, _dataCache).check();

    if (!readyResult.isReady()) {
      new MissingLoadRequestor(readyResult.getMissingList(), _dataCache, _loadRef).request();
      return false;
    }

    //TODO: 后面要做成 在此锁定数据后，发往execActor执行

    GameplayDataActor.CommandKit cmdKit = commandReq.getCommandKit();
    Object cmdParam = commandReq.getCommandParam();

    Class<?> resultType = cmdKit.getLoadResultType();
    Class<?> commandType = cmdKit.getCommandType();

    new CommandExecFinisher(resultType, cacheReq, _dataCache, commandType, cmdKit, cmdParam,
        _dataRef, _saveRef, commandReq.getRemoteRef()).finish();

    return true;
  }

  private final Queue<DataCommandRequest> _commandQueue;
  private final CacheContainer _dataCache;

  private final ActorMessageHandler.Ref _dataRef;
  private final ActorPreStartHandler.Actor _saveRef;
  private final ActorPreStartHandler.Actor _loadRef;
}
