package luj.game.server.internal.boot.plugin.start;

import java.util.List;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.plugin.JamverBootRootInit;

public class BootStartInvoker {

  public interface Result {

    Config config();

    Internal internal();
  }

  public interface Internal {

    /**
     * @see luj.game.server.api.plugin.JamverBootRootInit.Internal
     */
    Network network0();
  }

  public interface Network {

    void init(Tellable networkRef);
  }

  public interface Config {

    Cluster cluster();

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
    var network = new INetworkImpl();
    var ctx = new StartContextImpl();
    ctx._network = network;

    var config = (BootStartInvoker.Config) _startPlugin.onInit(ctx);
    var internal = new InternalImpl();
    internal._network = network;

    return new Result() {
      @Override
      public Config config() {
        return config;
      }

      @Override
      public Internal internal() {
        return internal;
      }
    };
  }

  private final JamverBootRootInit _startPlugin;
}
