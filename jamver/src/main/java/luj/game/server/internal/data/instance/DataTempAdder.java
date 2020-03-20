package luj.game.server.internal.data.instance;

import static com.google.common.base.Preconditions.checkNotNull;

import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;

@Deprecated
public class DataTempAdder {

  public DataTempAdder(CacheContainer dataCache, Class<?> dataType, DataTempProxy dataObj) {
    _dataCache = dataCache;
    _dataType = dataType;
    _dataObj = dataObj;
  }

  public void add() {
    Object dataId = _dataObj.getDataMap().get(DataTempProxy.ID);
    checkNotNull(dataId, _dataType);

    CacheItem cacheItem = new CacheItem(_dataType);
    cacheItem.setPresent(true);
    cacheItem.setDataObj(_dataObj);

    String dataKey = new CacheKeyMaker(_dataType, dataId).make();
    _dataCache.put(dataKey, cacheItem);
  }

  private final CacheContainer _dataCache;

  private final Class<?> _dataType;
  private final DataTempProxy _dataObj;
}
