package luj.game.server.internal.boot.plugin;

import java.util.List;
import luj.game.server.api.plugin.JamverBootRootInit;

public class BootStartInvoker {

  public interface Result {

    Cluster clusterConfig();

    Object startParam();
  }

  public interface Cluster {

    String selfHost();

    int selfPort();

    List<String> seedList();
  }

  public BootStartInvoker(JamverBootRootInit startPlugin) {
    _startPlugin = startPlugin;
  }

  public Result invoke() {
    ContextImpl ctx = new ContextImpl();
    return (Result) _startPlugin.onInit(ctx);
  }

  private final JamverBootRootInit _startPlugin;
}
