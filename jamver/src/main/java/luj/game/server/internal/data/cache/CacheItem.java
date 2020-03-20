package luj.game.server.internal.data.cache;

import luj.game.server.internal.data.instance.DataTempProxy;

public class CacheItem {

  public CacheItem(Class<?> dataType) {
    _dataType = dataType;
  }

  public boolean isPresent() {
    return _present;
  }

  public void setPresent(boolean present) {
    _present = present;
  }

  public DataTempProxy getDataObj() {
    return _dataObj;
  }

  public void setDataObj(DataTempProxy dataObj) {
    _dataObj = dataObj;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  private boolean _present;
  private DataTempProxy _dataObj;

  private final Class<?> _dataType;
}
