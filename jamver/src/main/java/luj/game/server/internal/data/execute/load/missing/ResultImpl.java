package luj.game.server.internal.data.execute.load.missing;

import java.util.Collection;
import java.util.List;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;

final class ResultImpl implements DataReadyChecker.Result {

  @Override
  public boolean isReady() {
    return _lockedOrLoading.isEmpty() && _missing.getLogMap().isEmpty();
  }

  @Override
  public Collection<DataReadyChecker.Missing> getMissingList() {
    return _missing.getLogMap().values();
  }

  List<CacheItem> _lockedOrLoading;

  MissingLog _missing;
}
