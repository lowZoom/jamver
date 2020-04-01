package luj.game.server.api.plugin;

import java.util.List;
import java.util.function.Function;

public interface JamverBootRootInit {

  interface Context {

    Return startConfig();
  }

  interface Return {

    Return cluster(Function<Cluster, Cluster> val);

    Return param(Object val);
  }

  interface Cluster {

    Cluster selfHost(String val);

    Cluster selfPort(int val);

    Cluster seedList(List<String> val);
  }

  Return onInit(Context ctx);
}
