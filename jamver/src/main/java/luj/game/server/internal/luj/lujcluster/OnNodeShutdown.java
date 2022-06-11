package luj.game.server.internal.luj.lujcluster;

import luj.ava.spring.Internal;
import luj.cluster.api.node.NodeShutdownListener;
import luj.game.server.api.plugin.JamverBootShutdown;
import luj.game.server.internal.boot.plugin.shutdown.BootShutdownInvoker;

@Internal
final class OnNodeShutdown implements NodeShutdownListener {

  @Override
  public void onShutdown(Context ctx) throws Exception {
    JambeanInLujcluster param = ctx.getStartParam();
    JamPluginCollect plugin = param.getAllPlugin();

    JamverBootShutdown shutdownPlugin = plugin.getBootShutdown();
    if (shutdownPlugin == null) {
      return;
    }

    new BootShutdownInvoker(shutdownPlugin, param.getAppShutdownParam()).invoke();
  }
}
