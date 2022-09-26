package luj.game.server.internal.data.init.root;

import luj.game.server.api.plugin.JamverDataRootInit;

public class DataRootInitInvoker {

  public DataRootInitInvoker(JamverDataRootInit initPlugin, Object startParam) {
    _initPlugin = initPlugin;
    _startParam = startParam;
  }

  public Object invoke() throws Exception {
    InitContextImpl ctx = new InitContextImpl();
    ctx._startParam = _startParam;

    return _initPlugin.onInit(ctx);
  }

  private final JamverDataRootInit _initPlugin;

  private final Object _startParam;
}
