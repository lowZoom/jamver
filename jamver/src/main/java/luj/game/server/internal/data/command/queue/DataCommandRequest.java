package luj.game.server.internal.data.command.queue;

import luj.cache.api.request.CacheRequest;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataCommandRequest {

  public DataCommandRequest(GameplayDataActor.CommandKit commandKit, Object commandParam,
      CacheRequest cacheReq, ServerMessageHandler.Server remoteRef) {
    _commandKit = commandKit;
    _commandParam = commandParam;
    _cacheReq = cacheReq;
    _remoteRef = remoteRef;
  }

  public GameplayDataActor.CommandKit getCommandKit() {
    return _commandKit;
  }

  public Object getCommandParam() {
    return _commandParam;
  }

  public CacheRequest getCacheReq() {
    return _cacheReq;
  }

  public ServerMessageHandler.Server getRemoteRef() {
    return _remoteRef;
  }

  private final GameplayDataActor.CommandKit _commandKit;

  private final Object _commandParam;
  private final CacheRequest _cacheReq;

  private final ServerMessageHandler.Server _remoteRef;
}
