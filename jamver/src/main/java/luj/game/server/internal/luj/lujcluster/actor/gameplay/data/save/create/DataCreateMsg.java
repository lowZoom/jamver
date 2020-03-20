package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create;

import java.util.Map;

public class DataCreateMsg {

  public DataCreateMsg(Class<?> dataType, Map<String, Object> dataMap) {
    _dataType = dataType;
    _dataMap = dataMap;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  public Map<String, Object> getDataMap() {
    return _dataMap;
  }

  private final Class<?> _dataType;

  private final Map<String, Object> _dataMap;
}
