package luj.game.server.internal.data.execute.load;

import luj.cache.api.CacheSession;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;

public class DataLoadRequestMaker {

  public DataLoadRequestMaker(GameDataLoad<?, ?> loader, Class<?> loadResultType, Object param,
      CacheSession lujcache) {
    _loader = loader;
    _loadResultType = loadResultType;
    _param = param;
    _lujcache = lujcache;
  }

  public CacheRequest make() {
    if (_loadResultType == Void.class) {
      return null;
    }

    CacheRequest req = _lujcache.createRequest(null);
    ResultFieldProxy fieldHolder = new ResultFieldProxy(_loadResultType).init();

    _loader.onLoad(new LoadContextImpl(_param, req, fieldHolder));
    return req;
  }

  private final GameDataLoad<?, ?> _loader;
  private final Class<?> _loadResultType;

  private final Object _param;
  private final CacheSession _lujcache;

}
