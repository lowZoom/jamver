package luj.game.server.internal.boot.plugin.start;

import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.plugin.JamverBootRootInit;

public class BootStartInvoker {

  public interface Result {

    Cluster clusterConfig();

    Inject injectExtra();

    Param param();
  }

  public interface Cluster {

    String selfHost();

    int selfPort();

    String selfName();

    List<String> selfTags();

    List<String> akkaSeed();

    String consulHost();

    int consulPort();
  }

  public interface Inject {

    List<GameStartListener> startListeners();
  }

  public interface Param {

    Object start();

    Object shutdown();
  }

  public BootStartInvoker(JamverBootRootInit startPlugin) {
    _startPlugin = startPlugin;
  }

  public Result invoke() throws Exception {
    StartContextImpl ctx = new StartContextImpl();
    return (Result) _startPlugin.onInit(ctx);
  }

  private final JamverBootRootInit _startPlugin;
}
