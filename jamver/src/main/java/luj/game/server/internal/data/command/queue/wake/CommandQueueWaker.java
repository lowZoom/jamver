package luj.game.server.internal.data.command.queue.wake;

import java.util.List;
import java.util.Queue;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.execute.finish.CommandExecFinisher;
import luj.game.server.internal.data.execute.load.missing.LoadMissingCollector;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class CommandQueueWaker {

  public CommandQueueWaker(Queue<DataCommandRequest> commandQueue, CacheContainer dataCache,
      ActorMessageHandler.Ref dataRef, ActorPreStartHandler.Actor saveRef) {
    _commandQueue = commandQueue;
    _dataCache = dataCache;
    _dataRef = dataRef;
    _saveRef = saveRef;
  }

  public void wake() {
    _commandQueue.removeIf(this::tryExecute);
  }

  private boolean tryExecute(DataCommandRequest commandReq) {
    CacheRequest cacheReq = commandReq.getCacheReq();
    List<LoadMissingCollector.Missing> missList =
        new LoadMissingCollector(cacheReq, _dataCache).collect();

    if (!missList.isEmpty()) {
      return false;
    }

    //TODO: 后面要做成在此锁定数据后，发往execActor执行

    GameplayDataActor.CommandKit cmdKit = commandReq.getCommandKit();
    Object cmdParam = commandReq.getCommandParam();

    new CommandExecFinisher(cmdKit.getLoadResultType(), cacheReq, _dataCache,
        cmdKit.getCommandType(), cmdKit, cmdParam, _dataRef, _saveRef).finish();

    return true;
  }

  private final Queue<DataCommandRequest> _commandQueue;
  private final CacheContainer _dataCache;

  private final ActorMessageHandler.Ref _dataRef;
  private final ActorPreStartHandler.Actor _saveRef;
}