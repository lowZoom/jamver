package luj.game.server.internal.network.accept;

import luj.game.server.api.net.NetAcceptHandler;

final class AddressImpl implements NetAcceptHandler.Address {

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
