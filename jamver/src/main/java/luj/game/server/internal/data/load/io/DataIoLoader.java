package luj.game.server.internal.data.load.io;

import luj.game.server.api.plugin.JamverDataLoadLoad;

public class DataIoLoader {

  public DataIoLoader(JamverDataLoadLoad loadPlugin, Object loadState, Class<?> dataType,
      Comparable<?> dataId, String dataIdField) {
    _loadPlugin = loadPlugin;
    _loadState = loadState;
    _dataType = dataType;
    _dataId = dataId;
    _dataIdField = dataIdField;
  }

  public void load() {
    DataRequestImpl dataReq = new DataRequestImpl(_dataType, _dataId, _dataIdField);
    LoadContextImpl ctx = new LoadContextImpl(_loadState, dataReq);

    _loadPlugin.onLoad(ctx);
  }

  private final JamverDataLoadLoad _loadPlugin;
  private final Object _loadState;

  private final Class<?> _dataType;
  private final Comparable<?> _dataId;
  private final String _dataIdField;
}
