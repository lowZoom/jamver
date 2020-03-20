package luj.game.server.internal.data.save.create.io;

import java.util.Map;
import luj.game.server.api.plugin.JamverDataSaveCreate;

public class DataIoCreator {

  public DataIoCreator(JamverDataSaveCreate createPlugin, Object saveState,
      Class<?> dataType, Map<String, ?> dataValue) {
    _createPlugin = createPlugin;
    _saveState = saveState;
    _dataType = dataType;
    _dataValue = dataValue;
  }

  @SuppressWarnings("unchecked")
  public void create() {
    CreateContextImpl ctx = new CreateContextImpl(_saveState,
        _dataType, (Map<String, Object>) _dataValue);

    _createPlugin.onCreate(ctx);
  }

  private final JamverDataSaveCreate _createPlugin;
  private final Object _saveState;

  private final Class<?> _dataType;
  private final Map<String, ?> _dataValue;
}
