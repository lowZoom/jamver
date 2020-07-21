package luj.game.server.internal.data.command.queue.add;

import java.util.Queue;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.data.command.queue.CommandRequestFactory;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class WaitQueueAdder {

  public WaitQueueAdder(Queue<DataCommandRequest> waitQueue,
      GameplayDataActor.CommandKit commandKit, Object commandParam,
      CacheRequest cacheReq, ServerMessageHandler.Server remoteRef) {
    _waitQueue = waitQueue;
    _commandKit = commandKit;
    _commandParam = commandParam;
    _cacheReq = cacheReq;
    _remoteRef = remoteRef;
  }

  public void add() {
    DataCommandRequest commandReq = new CommandRequestFactory(
        _remoteRef).createCommand(_commandKit, _commandParam, _cacheReq);

    _waitQueue.add(commandReq);
  }

  private final Queue<DataCommandRequest> _waitQueue;
  private final GameplayDataActor.CommandKit _commandKit;

  private final Object _commandParam;
  private final CacheRequest _cacheReq;

  private final ServerMessageHandler.Server _remoteRef;
}
