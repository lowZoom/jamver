package luj.game.server.internal.data.execute.load;

import luj.cache.api.CacheSession;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataLoadRequestMaker {

  public DataLoadRequestMaker(GameplayDataActor.CommandKit cmdKit, Object param,
      CacheSession lujcache) {
    _cmdKit = cmdKit;
    _param = param;
    _lujcache = lujcache;
  }

  public CacheRequest make() {
    Class<?> loadResultType = _cmdKit.getLoadResultType();
    if (loadResultType == Void.class) {
      return null;
    }

    CacheRequest req = _lujcache.createRequest(null);
    ResultFieldProxy fieldHolder = new ResultFieldProxy(loadResultType).init();

    GameDataLoad<?, ?> loader = _cmdKit.getLoader();
    loader.onLoad(new LoadContextImpl(_param, req, fieldHolder));

    return req;
  }

  private final GameplayDataActor.CommandKit _cmdKit;
  private final Object _param;

  private final CacheSession _lujcache;
}
