package luj.game.server.internal.data.instancev2;

import static com.google.common.base.Preconditions.checkNotNull;

import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;

public class CacheEntityAdder {

  public CacheEntityAdder(CacheContainer dataCache, DataEntity dataObj) {
    _dataCache = dataCache;
    _dataObj = dataObj;
  }

  public void add() {
    Object dataId = _dataObj.getDataId();
    DataType dataType = _dataObj.getDataType();
    checkNotNull(dataId, dataType.getName());

    CacheItem cacheItem = CacheItem.create(dataType);
    cacheItem.setPresence(DataPresence.PRESENT);
    cacheItem.setDataObjV2(_dataObj);

    //TODO: setLock(true)

    String dataKey = new CacheKeyMaker(dataType.getName(), dataId).make();
    _dataCache.put(dataKey, cacheItem);
  }

  private final CacheContainer _dataCache;

  private final DataEntity _dataObj;
}
