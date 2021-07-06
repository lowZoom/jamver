package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create;

import java.util.Map;

public class DataCreateMsg {

  public DataCreateMsg(String dataType, Comparable<?> dataId, String idField,
      Map<String, Object> dataMap) {
    _dataType = dataType;
    _dataId = dataId;
    _idField = idField;
    _dataMap = dataMap;
  }

  public String getDataType() {
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

  private final String _dataType;

  private final Comparable<?> _dataId;
  private final String _idField;

  private final Map<String, Object> _dataMap;
}
