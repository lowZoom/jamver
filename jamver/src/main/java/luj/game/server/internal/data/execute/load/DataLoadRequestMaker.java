package luj.game.server.internal.data.execute.load;

import java.util.HashMap;
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

  /**
   * @see luj.game.server.internal.luj.lujcache.OnWalkReq
   */
  public void make() {
    ResultFieldProxy fieldHolder = new ResultFieldProxy(_loadResultType).init();
    ResultDataProxy loadResult = new ResultDataProxy(_loadResultType, new HashMap<>());

    CacheRequest req = _lujcache.createRequest(null);
    _loader.onLoad(new LoadContextImpl(_param, req, fieldHolder, loadResult));

    req.walk();
  }

  private final GameDataLoad<?, ?> _loader;
  private final Class<?> _loadResultType;

  private final Object _param;
  private final CacheSession _lujcache;
}
