package luj.game.server.internal.data.save.create.io;

import java.util.Map;
import luj.game.server.api.plugin.JamverDataSaveCreate;

final class CreateContextImpl implements JamverDataSaveCreate.Context {

  CreateContextImpl(Object saveState, Class<?> dataType, Map<String, Object> dataValue) {
    _saveState = saveState;
    _dataType = dataType;
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
  public Map<String, Object> getDataValue() {
    return _dataValue;
  }

  private final Object _saveState;

  private final Class<?> _dataType;
  private final Map<String, Object> _dataValue;
}
