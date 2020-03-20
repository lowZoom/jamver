package luj.game.server.internal.data.save.update;

import java.util.Map;
import luj.game.server.api.plugin.JamverDataSaveUpdate;

final class UpdateContextImpl implements JamverDataSaveUpdate.Context {

  UpdateContextImpl(Object saveState, Class<?> dataType,
      JamverDataSaveUpdate.Id dataId, Map<String, Object> dataValue) {
    _saveState = saveState;
    _dataType = dataType;
    _dataId = dataId;
    _dataValue = dataValue;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getSaveState() {
    return (T) _saveState;
  }

  @Override
  public Class<?> getDataType() {
    return _dataType;
  }

  @Override
  public JamverDataSaveUpdate.Id getDataId() {
    return _dataId;
  }

  @Override
  public Map<String, Object> getDataValue() {
    return _dataValue;
  }

  private final Object _saveState;

  private final Class<?> _dataType;
  private final JamverDataSaveUpdate.Id _dataId;

  private final Map<String, Object> _dataValue;
}
