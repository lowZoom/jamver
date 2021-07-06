package luj.game.server.internal.data.cache;

import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.DataType;

public class CacheItem {

  public static CacheItem create(Class<?> dataType) {
    return new CacheItem(dataType, null);
  }

  public static CacheItem create(DataType dataType) {
    return new CacheItem(null, dataType);
  }

  public CacheItem(Class<?> dataType, DataType dataTypeV2) {
    _dataType = dataType;
    _dataTypeV2 = dataTypeV2;
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

  public DataEntity getDataObjV2() {
    return _dataObjV2;
  }

  public void setDataObjV2(DataEntity dataObjV2) {
    _dataObjV2 = dataObjV2;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  private DataPresence _presence;

  private DataTempProxy _dataObj;
  private DataEntity _dataObjV2;

  private final Class<?> _dataType;
  private final DataType _dataTypeV2;
}
