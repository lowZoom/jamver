package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create;

import java.io.Serializable;
import java.util.Map;

public class DataCreateMsg {

  public DataCreateMsg(Class<?> dataType, Map<String, Serializable> dataMap) {
    _dataType = dataType;
    _dataMap = dataMap;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  public Map<String, Serializable> getDataMap() {
    return _dataMap;
  }

  private final Class<?> _dataType;

  private final Map<String, Serializable> _dataMap;
}
