package luj.game.server.internal.boot.plugin.start;

import java.util.LinkedList;
import luj.game.server.api.plugin.JamverBootRootInit;

final class StartContextImpl implements JamverBootRootInit.Context {

  @Override
  public ReturnImpl startConfig() {
    ReturnImpl result = new ReturnImpl();
    result._cluster = new ClusterImpl();
    result._network = createNetwork();
    result._injectExtra = new InjectImpl();
    result._param = new ParamImpl();
    return result;
  }

  private NetworkImpl createNetwork() {
    NetworkImpl network = new NetworkImpl();

    NetAddrImpl address = new NetAddrImpl();
    network._address = address;

    address._addrList = new LinkedList<>();
    return network;
  }
}
