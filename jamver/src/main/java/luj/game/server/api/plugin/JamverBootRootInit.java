package luj.game.server.api.plugin;

import java.util.List;
import java.util.function.Function;
import luj.game.server.api.boot.GameStartListener;

public interface JamverBootRootInit {

  interface Context {

    Return startConfig();
  }

  interface Return {

    Return cluster(Function<Cluster, Cluster> val);

    Return network(Function<Network, Network> val);

    Return injectExtra(Function<Inject, Inject> val);

    Return param(Object val);
  }

  interface Cluster {

    Cluster selfHost(String val);

    Cluster selfPort(int val);

    Cluster seedList(List<String> val);
  }

  interface Network {

    interface Address {

      Address host(String val);

      Address port(int port);
    }

    Network bind(Function<Address, Address> addr);
  }

  interface Inject {

    Inject startListeners(List<GameStartListener> val);
  }

  Return onInit(Context ctx) throws Exception;
}
