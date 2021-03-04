package luj.game.server.internal.boot.plugin;

import java.util.List;
import luj.game.server.api.plugin.JamverBootRootInit;

final class NetAddrImpl implements JamverBootRootInit.Network.Address {

  @Override
  public JamverBootRootInit.Network.Address host(String val) {
    Addr addr = new Addr();
    addr._host = val;

    _addrList.add(addr);
    return this;
  }

  @Override
  public JamverBootRootInit.Network.Address port(int port) {
    Addr addr = (Addr) _addrList.get(_addrList.size() - 1);
    addr._port = port;
    return this;
  }

  static class Addr implements BootStartInvoker.Network.Address {

    @Override
    public String host() {
      return _host;
    }

    @Override
    public int port() {
      return _port;
    }

    String _host;

    int _port;
  }

  List<BootStartInvoker.Network.Address> _addrList;
}
