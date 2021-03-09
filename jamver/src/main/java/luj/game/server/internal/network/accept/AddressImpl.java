package luj.game.server.internal.network.accept;

import luj.game.server.api.net.GameAcceptHandler;
import luj.net.api.server.ConnectionAcceptInitializer;

final class AddressImpl implements GameAcceptHandler.Address {

  @Override
  public String host() {
    return _addr.host();
  }

  @Override
  public int port() {
    return _addr.port();
  }

  ConnectionAcceptInitializer.Address _addr;
}
