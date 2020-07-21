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
    List<CacheItem> lockedOrLoading = new ArrayList<>();
    List<Missing> missing = new ArrayList<>();

    ReadyWalker walker = new ReadyWalker(_cache, lockedOrLoading, missing);
    for (CacheRequest req : _reqList) {
      req.walk(walker);
    }

    return new ResultImpl(lockedOrLoading, missing);
  }

  private final List<CacheRequest> _reqList;

  private final CacheContainer _cache;
}
