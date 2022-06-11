package luj.game.server.internal.boot.plugin.shutdown;

import luj.game.server.api.plugin.JamverBootShutdown;

public class BootShutdownInvoker {

  public BootShutdownInvoker(JamverBootShutdown shutdownPlugin, Object shutdownParam) {
    _shutdownPlugin = shutdownPlugin;
    _shutdownParam = shutdownParam;
  }

  public void invoke() throws Exception {
    ShutdownContextImpl ctx = new ShutdownContextImpl();
    ctx._shutdownParam = _shutdownParam;

    _shutdownPlugin.onShutdown(ctx);
  }

  private final JamverBootShutdown _shutdownPlugin;

  private final Object _shutdownParam;
}
