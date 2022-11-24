package luj.game.server.api.plugin;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import luj.game.server.api.boot.GameStartListener;

public interface JamverBootRootInit {

  interface Context {

    Return startConfig();
  }

  interface Return {

    Return cluster(Function<Cluster, Cluster> val);

    /**
     * @see JamverDynamicRootInit.Context#registerAll
     */
    @Deprecated
    Return injectExtra(Function<Inject, Inject> val);

    Return param(Function<Param, Param> val);
  }

  interface Cluster {

    Cluster selfHost(String val);

    Cluster selfPort(int val);

    Cluster selfName(String val);

    Cluster selfTags(Set<String> val);

    Cluster discoveryAkkaSeed(List<String> val);

    Cluster discoveryConsulHost(String val);

    Cluster discoveryConsulPort(int val);
  }

  interface Inject {

    Inject startListeners(List<GameStartListener> val);
  }

  interface Param {

    Param start(Object val);

    Param shutdown(Object val);
  }

  Return onInit(Context ctx) throws Exception;
}
