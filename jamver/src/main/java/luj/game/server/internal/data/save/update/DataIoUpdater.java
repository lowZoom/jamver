package luj.game.server.internal.data.save.update;

import java.util.HashMap;
import java.util.Map;
import luj.game.server.api.plugin.JamverDataSaveUpdate;

public class DataIoUpdater {

  public DataIoUpdater(JamverDataSaveUpdate updatePlugin, Object saveState, Class<?> dataType,
      Map<String, Object> dataValue, Comparable<?> dataId, String idField) {
    _updatePlugin = updatePlugin;
    _saveState = saveState;
    _dataType = dataType;
    _dataValue = dataValue;
    _dataId = dataId;
    _idField = idField;
  }

  public void update() {
    IdImpl id = new IdImpl(_dataId, _idField);

    // 拷一份避免被外部修改
    Map<String, Object> valueMap = new HashMap<>(_dataValue);

    UpdateContextImpl ctx = new UpdateContextImpl(_saveState, _dataType, id, valueMap);
    _updatePlugin.onUpdate(ctx);
  }

  private final JamverDataSaveUpdate _updatePlugin;
  private final Object _saveState;

  private final Class<?> _dataType;
  private final Map<String, Object> _dataValue;

  private final Comparable<?> _dataId;
  private final String _idField;
}
