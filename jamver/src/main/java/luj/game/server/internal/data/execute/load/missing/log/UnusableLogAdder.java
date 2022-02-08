package luj.game.server.internal.data.execute.load.missing.log;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.DataPresence;

public enum UnusableLogAdder {
  GET;

  public void add(CacheItem item, Class<?> dataType, Comparable<?> dataId, MissingLog missingOut,
      List<CacheItem> lockedOrLoadingOut) {
    if (item == null) {
      MissingLogAdder.GET.add(missingOut, dataType, dataId);
      return;
    }

    boolean locked = item.getLocker() != null;
    DataPresence presence = item.getPresence();
    if (locked) {
      checkState(presence != DataPresence.ABSENT, "%s#%s", dataType.getSimpleName(), dataId);
    }

    if (locked || presence == DataPresence.LOADING) {
      lockedOrLoadingOut.add(item);
    }
  }
}
