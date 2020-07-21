package luj.game.server.internal.data.command.queue.element;

import luj.cache.api.request.CacheRequest;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class GroupReqElement {

  public GroupReqElement(GameplayDataActor.CommandKit commandKit, Object commandParam,
      CacheRequest cacheReq) {
    _commandKit = commandKit;
    _commandParam = commandParam;
    _cacheReq = cacheReq;
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

  private final GameplayDataActor.CommandKit _commandKit;

  private final Object _commandParam;
  private final CacheRequest _cacheReq;
}
