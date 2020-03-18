package luj.game.server.internal.data.save.init;

import luj.game.server.api.plugin.JamverDataSaveInit;

public class DataSaveInitializer {

  public DataSaveInitializer(JamverDataSaveInit initPlugin, Object rootState) {
    _initPlugin = initPlugin;
    _rootState = rootState;
  }

  public Object init() {
    InitContextImpl ctx = new InitContextImpl(_rootState);
    return _initPlugin.onInit(ctx);
  }

  private final JamverDataSaveInit _initPlugin;

  private final Object _rootState;
}
