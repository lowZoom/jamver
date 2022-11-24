package luj.game.server.internal.boot.plugin.start;

import luj.game.server.api.plugin.JamverBootRootInit;

final class StartContextImpl implements JamverBootRootInit.Context {

  @Override
  public ReturnImpl startConfig() {
    var result = new ReturnImpl();
    result._cluster = new ClusterImpl();

    result._injectExtra = new InjectImpl();
    result._param = new ParamImpl();

    return result;
  }
}
