package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.load;

import java.util.Map;

public class LoadFinishMsg {

  public LoadFinishMsg(Class<?> dataType, Comparable<?> dataId, boolean present,
      Map<String, Object> dataValue) {
    _dataType = dataType;
    _dataId = dataId;
    _present = present;
    _dataValue = dataValue;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  public Comparable<?> getDataId() {
    return _dataId;
  }

  public boolean isPresent() {
    return _present;
  }

  public Map<String, Object> getDataValue() {
    return _dataValue;
  }

  private final Class<?> _dataType;
  private final Comparable<?> _dataId;

  private final boolean _present;
  private final Map<String, Object> _dataValue;
}
