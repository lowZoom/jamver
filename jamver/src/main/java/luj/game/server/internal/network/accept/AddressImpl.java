package luj.game.server.internal.network.accept;

import luj.game.server.api.net.GameAcceptHandler;

final class AddressImpl implements GameAcceptHandler.Address {

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
