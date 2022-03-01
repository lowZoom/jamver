package luj.game.server.internal.data.execute.load.missing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;

public class DataReadyChecker {

  public interface Result {

    boolean isReady();

    Collection<Missing> getMissingList();
  }

  public interface Missing {

    Class<?> dataType();

    Comparable<?> dataId();
  }

  public DataReadyChecker(List<CacheRequest> reqList) {
    _reqList = reqList;
  }

  public Result check() {
    List<CacheItem> lockedOrLoading = new ArrayList<>();
    MissingLog missing = new MissingLog(new HashMap<>());

    ReadyWalker walker = new ReadyWalker();
    walker._lockedOrLoadingOut = lockedOrLoading;
    walker._missingOut = missing;

    for (CacheRequest req : _reqList) {
      req.walk(walker);
    }

    return newResult(lockedOrLoading, missing);
  }

  public static Result newResult(List<CacheItem> lockedOrLoading, MissingLog missing) {
    ResultImpl result = new ResultImpl();
    result._lockedOrLoading = lockedOrLoading;
    result._missing = missing;
    return result;
  }

  private final List<CacheRequest> _reqList;
}
