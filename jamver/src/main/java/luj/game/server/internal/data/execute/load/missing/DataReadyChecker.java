package luj.game.server.internal.data.execute.load.missing;

import java.util.ArrayList;
import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.game.server.internal.data.cache.CacheItem;

public class DataReadyChecker {

  public interface Result {

    boolean isReady();

    List<Missing> getMissingList();
  }

  public interface Missing {

    Class<?> dataType();

    Comparable<?> dataId();
  }

  public DataReadyChecker(CacheRequest cacheReq, CacheContainer cache) {
    _cacheReq = cacheReq;
    _cache = cache;
  }

  public Result check() {
    List<CacheItem> lockedOrLoading = new ArrayList<>();
    List<Missing> missing = new ArrayList<>();

    _cacheReq.walk(new ReadyWalker(_cache, lockedOrLoading, missing));
    return new ResultImpl(lockedOrLoading, missing);
  }

  private final CacheRequest _cacheReq;

  private final CacheContainer _cache;
}
