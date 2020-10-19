package luj.game.server.internal.data.save.wait;

import java.util.Map;

public class BatchCreateItem {

  public BatchCreateItem(Class<?> dataType, Map<String, Object> dataValue) {
    _dataType = dataType;
    _dataValue = dataValue;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  public Map<String, Object> getDataValue() {
    return _dataValue;
  }

  private final Class<?> _dataType;

  private final Map<String, Object> _dataValue;
}
