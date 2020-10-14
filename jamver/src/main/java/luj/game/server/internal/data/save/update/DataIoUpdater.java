package luj.game.server.internal.data.save.update;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import luj.game.server.api.plugin.JamverDataSaveUpdate;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;

public class DataIoUpdater {

  public DataIoUpdater(JamverDataSaveUpdate updatePlugin, Object saveState, Class<?> dataType,
      Map<String, Object> changedFields, Map<String, DUpdateMsgSet> setChanged,
      Map<String, DUpdateMsgMap> mapChanged, Comparable<?> dataId, String idField) {
    _updatePlugin = updatePlugin;
    _saveState = saveState;
    _dataType = dataType;
    _changedFields = changedFields;
    _setChanged = setChanged;
    _mapChanged = mapChanged;
    _dataId = dataId;
    _idField = idField;
  }

  public void update() {
    IdImpl id = new IdImpl(_dataId, _idField);

    ChangedImpl changed = new ChangedImpl();
    changed._primitive = _changedFields;

    changed._set = _setChanged.entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> makeSet(e.getValue())));

    changed._map = _mapChanged.entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> makeMap(e.getValue())));

    UpdateContextImpl ctx = new UpdateContextImpl(_saveState, _dataType, id, changed);
    _updatePlugin.onUpdate(ctx);
  }

  private CSetImpl makeSet(DUpdateMsgSet msg) {
    CSetImpl set = new CSetImpl();
    set._set = msg;
    return set;
  }

  private CMapImpl makeMap(DUpdateMsgMap msg) {
    CMapImpl map = new CMapImpl();
    map._map = msg;
    return map;
  }

  private final JamverDataSaveUpdate _updatePlugin;
  private final Object _saveState;

  private final Class<?> _dataType;
  private final Map<String, Object> _changedFields;

  private final Map<String, DUpdateMsgSet> _setChanged;
  private final Map<String, DUpdateMsgMap> _mapChanged;

  private final Comparable<?> _dataId;
  private final String _idField;
}
