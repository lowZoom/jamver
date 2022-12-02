package luj.game.server.api.plugin;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public interface JamverBootRootInit {

  interface Context {

    Internal internal();

    Return startConfig();
  }

  interface Internal {

    Network network();
  }

  interface Network {

    void receiveProto(String key, Object proto);
  }

  interface Return {

    Return cluster(Function<Cluster, Cluster> val);

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

  interface Param {

    Param start(Object val);

    Param shutdown(Object val);
  }

  Return onInit(Context ctx) throws Exception;
}
