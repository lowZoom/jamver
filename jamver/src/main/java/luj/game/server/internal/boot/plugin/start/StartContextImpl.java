package luj.game.server.internal.boot.plugin.start;

import luj.game.server.api.plugin.JamverBootRootInit;

final class StartContextImpl implements JamverBootRootInit.Context {

  @Override
  public JamverBootRootInit.Internal internal() {
    var result = new InternalImpl();
    result._network = _network;

    return result;
  }

  @Override
  public ReturnImpl startConfig() {
    var result = new ReturnImpl();
    result._cluster = new RClusterImpl();
    result._param = new RParamImpl();

    return result;
  }

  JamverBootRootInit.Network _network;
}
