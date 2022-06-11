package luj.game.server.internal.boot.plugin.start;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.function.Function;
import luj.game.server.api.plugin.JamverBootRootInit;

final class NetworkImpl implements JamverBootRootInit.Network, BootStartInvoker.Network {

  @Override
  public JamverBootRootInit.Network bind(
      Function<JamverBootRootInit.Network.Address, JamverBootRootInit.Network.Address> addr) {
    checkState(addr.apply(_address) == _address);
    return this;
  }

  /////////////////////////////////////////////

  @Override
  public List<BootStartInvoker.Network.Address> bind() {
    return _address._addrList;
  }

  /////////////////////////////////////////////

  NetAddrImpl _address;
}
