package luj.game.server.internal.data.execute.load.missing.log;

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
    if (locked || item.getPresence() == DataPresence.LOADING) {
      lockedOrLoadingOut.add(item);
    }
  }
}
