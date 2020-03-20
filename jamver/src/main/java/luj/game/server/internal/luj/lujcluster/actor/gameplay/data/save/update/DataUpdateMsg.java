package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update;

import java.util.Map;

public class DataUpdateMsg {

  public DataUpdateMsg(Class<?> dataType, Comparable<?> dataId,
      String idField, Map<String, Object> dataMap) {
    _dataType = dataType;
    _dataId = dataId;
    _idField = idField;
    _dataMap = dataMap;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  public Comparable<?> getDataId() {
    return _dataId;
  }

  public String getIdField() {
    return _idField;
  }

  public Map<String, Object> getDataMap() {
    return _dataMap;
  }

  private final Class<?> _dataType;

  private final Comparable<?> _dataId;
  private final String _idField;

  private final Map<String, Object> _dataMap;
}
