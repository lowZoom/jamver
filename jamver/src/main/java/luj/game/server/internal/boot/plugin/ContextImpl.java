package luj.game.server.internal.boot.plugin;

import luj.game.server.api.plugin.JamverBootRootInit;

final class ContextImpl implements JamverBootRootInit.Context {

  @Override
  public ReturnImpl startConfig() {
    ClusterImpl cluster = new ClusterImpl();
    return new ReturnImpl(cluster);
  }
}
