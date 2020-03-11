package luj.game.server.internal.data.instance;

import static com.google.common.base.Preconditions.checkNotNull;

import luj.cache.api.container.CacheContainer;

@Deprecated
public class DataTempAdder {

  public DataTempAdder(CacheContainer dataCache, Class<?> dataType, DataTempProxy dataObj) {
    _dataCache = dataCache;
    _dataType = dataType;
    _dataObj = dataObj;
  }

  public void add() {
    Object dataId = _dataObj.getDataMap().get("id");
    checkNotNull(dataId, _dataType);
    _dataCache.put(dataKey(_dataType, dataId), _dataObj);
  }

  private String dataKey(Class<?> dataType, Object dataId) {
    return dataType.getName() + "#" + dataId;
  }

  private final CacheContainer _dataCache;

  private final Class<?> _dataType;
  private final DataTempProxy _dataObj;
}
