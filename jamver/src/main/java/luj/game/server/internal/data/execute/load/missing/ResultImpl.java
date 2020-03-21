package luj.game.server.internal.data.execute.load.missing;

import java.util.List;
import luj.game.server.internal.data.cache.CacheItem;

final class ResultImpl implements DataReadyChecker.Result {

  ResultImpl(List<CacheItem> lockedOrLoading, List<DataReadyChecker.Missing> missing) {
    _lockedOrLoading = lockedOrLoading;
    _missing = missing;
  }

  @Override
  public boolean isReady() {
    return _lockedOrLoading.isEmpty() && _missing.isEmpty();
  }

  @Override
  public List<DataReadyChecker.Missing> getMissingList() {
    return _missing;
  }

  private final List<CacheItem> _lockedOrLoading;

  private final List<DataReadyChecker.Missing> _missing;
}
