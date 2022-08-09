package luj.game.server.internal.data.execute.load.request.node.find.finish.lock;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Map;
import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.instancev2.DataEntity;

public enum DataObjGetAndLocker {
  GET;

  public DataPair getAndLock(CacheContainer dataCache, Class<?> dataType, Comparable<?> dataId,
      Map<String, DataPair> loadLog) {
    String dataKey = CacheKeyMaker.create(dataType, dataId).make();
//    LOG.debug("读取读取读取读取：{}", dataKey);

    DataPair oldData = loadLog.get(dataKey);
    if (oldData != null) {
      return oldData;
    }

    CacheItem cacheItem = dataCache.get(dataKey);
    checkNotNull(cacheItem, dataKey);

    String locker = cacheItem.getLocker();
    checkState(locker == null, "%s：%s", dataKey, locker);

    DataPresence presence = cacheItem.getPresence();
    DataEntity dataObj = cacheItem.getDataObjV2();
    if (presence == DataPresence.ABSENT) {
      checkState(dataObj == null, dataKey);
      return null;
    }

    //TODO: 记录真正的上锁者
    cacheItem.setLocker("lock");

    checkState(presence == DataPresence.PRESENT, "%s：%s", dataKey, presence);
    checkNotNull(dataObj, dataKey);

    return new DataPair(dataObj, dataKey);
  }
}
