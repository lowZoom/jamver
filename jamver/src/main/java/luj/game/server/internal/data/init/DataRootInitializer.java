package luj.game.server.internal.data.init;

import luj.game.server.api.plugin.JamverDataRootInit;

public class DataRootInitializer {

  public DataRootInitializer(JamverDataRootInit initPlugin, Object startParam) {
    _initPlugin = initPlugin;
    _startParam = startParam;
  }

  public Object init() {
    InitContextImpl ctx = new InitContextImpl(_startParam);
    return _initPlugin.onInit(ctx);
  }

  private final JamverDataRootInit _initPlugin;

  private final Object _startParam;
}
