package luj.game.server.internal.data.cache;

import luj.game.server.internal.data.instance.DataTempProxy;

public class CacheItem {

  public CacheItem(Class<?> dataType) {
    _dataType = dataType;
  }

  public DataPresence getPresence() {
    return _presence;
  }

  public void setPresence(DataPresence presence) {
    _presence = presence;
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

  private DataPresence _presence;
  private DataTempProxy _dataObj;

  private final Class<?> _dataType;
}
