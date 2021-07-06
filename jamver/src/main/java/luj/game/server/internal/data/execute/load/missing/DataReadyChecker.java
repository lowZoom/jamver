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

  public DataReadyChecker(List<CacheRequest> reqList, CacheContainer cache) {
    _reqList = reqList;
    _cache = cache;
  }

  public Result check() {
    ReadyWalker walker = new ReadyWalker();
    walker._cache = _cache;

    List<CacheItem> lockedOrLoading = new ArrayList<>();
    walker._lockedOrLoadingOut = lockedOrLoading;

    List<Missing> missing = new ArrayList<>();
    walker._missingOut = missing;

    for (CacheRequest req : _reqList) {
      req.walk(walker);
    }

    return newResult(lockedOrLoading, missing);
  }

  public static Result newResult(List<CacheItem> lockedOrLoading, List<Missing> missing) {
    ResultImpl result = new ResultImpl();
    result._lockedOrLoading = lockedOrLoading;
    result._missing = missing;
    return result;
  }

  private final List<CacheRequest> _reqList;

  private final CacheContainer _cache;
}
