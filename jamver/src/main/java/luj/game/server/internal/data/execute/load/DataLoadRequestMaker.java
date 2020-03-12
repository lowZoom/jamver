package luj.game.server.internal.data.execute.load;

import java.util.HashMap;
import java.util.function.BiConsumer;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.data.instance.DataTempProxy;

public class DataLoadRequestMaker {

  public interface Context {

    Object getLoadResult();

    CacheContainer getDataCache();

    BiConsumer<DataTempProxy, String> getFieldHook();
  }

  public DataLoadRequestMaker(GameDataLoad<?, ?> loader, Class<?> loadResultType, Object param,
      CacheContainer dataCache, CacheSession lujcache,
      BiConsumer<DataTempProxy, String> fieldHook) {
    _loader = loader;
    _loadResultType = loadResultType;
    _param = param;
    _dataCache = dataCache;
    _lujcache = lujcache;
    _fieldHook = fieldHook;
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

      @Override
      public BiConsumer<DataTempProxy, String> getFieldHook() {
        return _fieldHook;
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

  @Deprecated
  private final BiConsumer<DataTempProxy, String> _fieldHook;
}
