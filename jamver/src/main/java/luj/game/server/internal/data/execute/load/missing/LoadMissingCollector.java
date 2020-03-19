package luj.game.server.internal.data.execute.load.missing;

import java.util.ArrayList;
import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;

public class LoadMissingCollector {

  public interface Missing {

    Class<?> dataType();

    Comparable<?> dataId();
  }

  public LoadMissingCollector(CacheRequest cacheReq, CacheContainer cache) {
    _cacheReq = cacheReq;
    _cache = cache;
  }

  public List<Missing> collect() {
    List<Missing> result = new ArrayList<>();
    _cacheReq.walk(new MissingWalker(_cache, result));

    return result;
  }

  private final CacheRequest _cacheReq;

  private final CacheContainer _cache;
}
