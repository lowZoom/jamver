package luj.game.server.internal.data.execute.load;

import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.data.execute.load.result.ResultFieldProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataLoadRequestMaker {

  public interface ContextFactory {

    GameDataLoad.Context create(CacheRequest cacheReq, ResultFieldProxy fieldHolder);
  }

  public static DataLoadRequestMaker create(GameplayDataActor.CommandKit cmdKit,
      Object param, CacheContainer dataCache, CacheSession lujcache) {
    return new DataLoadRequestMaker(cmdKit, lujcache,
        (r, f) -> new LoadContextImpl(param, dataCache, r, f));
  }

  public DataLoadRequestMaker(GameplayDataActor.CommandKit cmdKit,
      CacheSession lujcache, ContextFactory contextFactory) {
    _cmdKit = cmdKit;
    _lujcache = lujcache;
    _contextFactory = contextFactory;
  }

  public CacheRequest make() {
    Class<?> loadResultType = _cmdKit.getLoadResultType();
    if (loadResultType == Void.class) {
      return null;
    }

    CacheRequest req = _lujcache.createRequest(null);
    ResultFieldProxy fieldHolder = ResultFieldProxy.create(loadResultType);

    GameDataLoad<?, ?> loader = _cmdKit.getLoader();
    loader.onLoad(_contextFactory.create(req, fieldHolder));

    return req;
  }

  private final GameplayDataActor.CommandKit _cmdKit;

  private final CacheSession _lujcache;
  private final ContextFactory _contextFactory;
}
