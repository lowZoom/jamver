package luj.game.server.internal.data.save.update;

import luj.game.server.api.plugin.JamverDataSaveUpdate;

final class UpdateContextImpl implements JamverDataSaveUpdate.Context {

  UpdateContextImpl(Object saveState, Class<?> dataType,
      JamverDataSaveUpdate.Id dataId, JamverDataSaveUpdate.Changed changedFields) {
    _saveState = saveState;
    _dataType = dataType;
    _dataId = dataId;
    _changedFields = changedFields;
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
  public JamverDataSaveUpdate.Changed getChangedFields() {
    return _changedFields;
  }

  private final Object _saveState;

  private final Class<?> _dataType;
  private final JamverDataSaveUpdate.Id _dataId;

  private final JamverDataSaveUpdate.Changed _changedFields;
}
