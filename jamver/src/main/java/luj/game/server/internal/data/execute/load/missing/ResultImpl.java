package luj.game.server.internal.data.execute.load.missing;

import java.util.List;
import luj.game.server.internal.data.cache.CacheItem;

final class ResultImpl implements DataReadyChecker.Result {

  @Override
  public boolean isReady() {
    return _lockedOrLoading.isEmpty() && _missing.isEmpty();
  }

  @Override
  public List<DataReadyChecker.Missing> getMissingList() {
    return _missing;
  }

  List<CacheItem> _lockedOrLoading;

  List<DataReadyChecker.Missing> _missing;
}
