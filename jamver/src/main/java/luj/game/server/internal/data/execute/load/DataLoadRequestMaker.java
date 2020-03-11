package luj.game.server.internal.data.execute.load;

import java.util.HashMap;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;

public class DataLoadRequestMaker {

  public interface Context {

    Object getLoadResult();

    CacheContainer getDataCache();
  }

  public DataLoadRequestMaker(GameDataLoad<?, ?> loader, Class<?> loadResultType, Object param,
      CacheContainer dataCache, CacheSession lujcache) {
    _loader = loader;
    _loadResultType = loadResultType;
    _param = param;
    _dataCache = dataCache;
    _lujcache = lujcache;
  }

  /**
   * @see luj.game.server.internal.luj.lujcache.OnWalkReq
   */
  public Object make() {
    ResultFieldProxy fieldHolder = new ResultFieldProxy(_loadResultType).init();
    ResultDataProxy loadResult = new ResultDataProxy(_loadResultType, new HashMap<>()).init();

    CacheRequest req = _lujcache.createRequest(new Context() {
      @Override
      public Object getLoadResult() {
        return loadResult;
      }

      @Override
      public CacheContainer getDataCache() {
        return _dataCache;
      }
    });

    _loader.onLoad(new LoadContextImpl(_param, req, fieldHolder));
    req.walk();

    return loadResult.getInstance();
  }

  private final GameDataLoad<?, ?> _loader;
  private final Class<?> _loadResultType;

  private final Object _param;
  private final CacheContainer _dataCache;

  private final CacheSession _lujcache;
}
