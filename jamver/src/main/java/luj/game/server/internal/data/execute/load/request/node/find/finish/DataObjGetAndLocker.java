package luj.game.server.internal.data.execute.load.request.node.find.finish;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.instancev2.DataEntity;

public enum DataObjGetAndLocker {
  GET;

  public DataEntity getAndLock(CacheContainer dataCache, Class<?> dataType, Comparable<?> dataId) {
    String dataKey = CacheKeyMaker.create(dataType, dataId).make();
//    LOG.debug("读取读取读取读取：{}", dataKey);

    CacheItem cacheItem = dataCache.get(dataKey);
    checkNotNull(cacheItem, dataKey);

    String locker = cacheItem.getLocker();
    checkState(locker == null, "%s：%s", dataKey, locker);

    //TODO: 记录真正的上锁者
    cacheItem.setLocker("lock");

    DataPresence presence = cacheItem.getPresence();
    DataEntity dataObj = cacheItem.getDataObjV2();

    if (presence == DataPresence.ABSENT) {
      checkState(dataObj == null, dataKey);
      return null;
    }

    checkState(presence == DataPresence.PRESENT, "%s：%s", dataKey, presence);
    return checkNotNull(dataObj, dataKey);
  }
}
