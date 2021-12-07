package luj.game.server.internal.data.lock;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.DataType;

public enum CacheItemUnlocker {
  GET;

  public void unlock(CacheContainer cache, DataEntity data) {
    DataType dataType = data.getDataType();
    Comparable<?> dataId = data.getDataId();

    String dataKey = new CacheKeyMaker(dataType.getName(), dataId).make();
    CacheItem item = cache.get(dataKey);
    checkNotNull(item, dataKey);

    String locker = item.getLocker();
    checkState(locker != null, "%s：%s", dataKey, locker);

    item.setLocker(null);
  }
}
