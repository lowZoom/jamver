package luj.game.server.internal.data.execute.load.request.node.find.ready;

import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.instancev2.DataType;

public enum NodeIdOneFindReady {
  GET;

  public Object find(CacheContainer cache, Class<?> dataType, Comparable<?> dataId,
      List<DataReadyChecker.Missing> missingOut, List<CacheItem> lockedOrLoadingOut) {
    CacheItem dataItem = cacheGet(cache, dataType, dataId);
    logUnusable(dataItem, dataType, dataId, missingOut, lockedOrLoadingOut);

    updateClassCache(dataItem, dataType);
    return dataItem;
  }

  CacheItem cacheGet(CacheContainer cache, Class<?> dataType, Object dataId) {
    String dataKey = CacheKeyMaker.create(dataType, dataId).make();
    return cache.get(dataKey);
  }

  void logUnusable(CacheItem item, Class<?> dataType, Comparable<?> dataId,
      List<DataReadyChecker.Missing> missingOut, List<CacheItem> lockedOrLoadingOut) {
    if (item == null) {
      missingOut.add(new MissingImpl(dataType, dataId));
      return;
    }

    if (item.getPresence() == DataPresence.LOADING) {
      lockedOrLoadingOut.add(item);
    }
  }

  private void updateClassCache(CacheItem item, Class<?> dataType) {
    if (item == null) {
      return;
    }

    DataType type = item.getDataTypeV2();
    type.setClassCache(dataType);
  }
}
