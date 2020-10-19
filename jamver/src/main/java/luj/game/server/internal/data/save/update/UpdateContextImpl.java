package luj.game.server.internal.data.save.update;

import luj.game.server.api.plugin.JamverDataSaveUpdate;

final class UpdateContextImpl implements JamverDataSaveUpdate.Context {

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

  Object _saveState;

  Class<?> _dataType;
  JamverDataSaveUpdate.Id _dataId;

  JamverDataSaveUpdate.Changed _changedFields;
}
