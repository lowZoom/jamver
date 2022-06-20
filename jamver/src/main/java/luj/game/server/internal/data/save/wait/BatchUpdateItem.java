package luj.game.server.internal.data.save.wait;

import java.util.Map;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;

public class BatchUpdateItem {

  public BatchUpdateItem(String dataType, Comparable<?> dataId, String idField,
      Map<String, Object> primitiveUpdated, Map<String, DUpdateMsgSet> setUpdated,
      Map<String, DUpdateMsgMap> mapUpdated) {
    _dataType = dataType;
    _dataId = dataId;
    _idField = idField;
    _primitiveUpdated = primitiveUpdated;
    _setUpdated = setUpdated;
    _mapUpdated = mapUpdated;
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

  public Map<String, Object> getPrimitiveUpdated() {
    return _primitiveUpdated;
  }

  public Map<String, DUpdateMsgSet> getSetUpdated() {
    return _setUpdated;
  }

  public Map<String, DUpdateMsgMap> getMapUpdated() {
    return _mapUpdated;
  }

  private final String _dataType;

  private final Comparable<?> _dataId;
  private final String _idField;

  private final Map<String, Object> _primitiveUpdated;
  private final Map<String, DUpdateMsgSet> _setUpdated;
  private final Map<String, DUpdateMsgMap> _mapUpdated;
}
