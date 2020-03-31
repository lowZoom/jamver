package luj.game.server.internal.boot.plugin;

import luj.game.server.api.plugin.JamverBootRootInit;

public class BootStartInvoker {

  public BootStartInvoker(JamverBootRootInit startPlugin) {
    _startPlugin = startPlugin;
  }

  public Object invoke() {
    ContextImpl ctx = new ContextImpl();

    return _startPlugin.onInit(ctx);
  }

  private final JamverBootRootInit _startPlugin;
}
