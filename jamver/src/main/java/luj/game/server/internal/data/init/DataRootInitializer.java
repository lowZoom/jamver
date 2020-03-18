package luj.game.server.internal.data.init;

import luj.game.server.api.plugin.JamverDataRootInit;

public class DataRootInitializer {

  public DataRootInitializer(JamverDataRootInit initPlugin) {
    _initPlugin = initPlugin;
  }

  public Object init() {
    InitContextImpl ctx = new InitContextImpl();
    return _initPlugin.onInit(ctx);
  }

  private final JamverDataRootInit _initPlugin;
}
