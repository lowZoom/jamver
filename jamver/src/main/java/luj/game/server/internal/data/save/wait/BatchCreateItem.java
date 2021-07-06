package luj.game.server.internal.data.save.wait;

import java.util.Map;

public class BatchCreateItem {

  public BatchCreateItem(String dataType, Comparable<?> dataId,
      String idField, Map<String, Object> dataValue) {
    _dataType = dataType;
    _dataId = dataId;
    _idField = idField;
    _dataValue = dataValue;
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

  public Map<String, Object> getDataValue() {
    return _dataValue;
  }

  private final String _dataType;

  private final Comparable<?> _dataId;
  private final String _idField;

  private final Map<String, Object> _dataValue;
}
