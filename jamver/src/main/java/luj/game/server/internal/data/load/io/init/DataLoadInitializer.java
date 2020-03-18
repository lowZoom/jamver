package luj.game.server.internal.data.load.io.init;

import luj.game.server.api.plugin.JamverDataLoadInit;

public class DataLoadInitializer {

  public DataLoadInitializer(JamverDataLoadInit initPlugin, Object rootState) {
    _initPlugin = initPlugin;
    _rootState = rootState;
  }

  public Object init() {
    InitContextImpl ctx = new InitContextImpl(_rootState);
    return _initPlugin.onInit(ctx);
  }

  private final JamverDataLoadInit _initPlugin;

  private final Object _rootState;
}
