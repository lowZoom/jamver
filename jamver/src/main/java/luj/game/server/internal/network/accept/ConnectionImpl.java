package luj.game.server.internal.network.accept;

import luj.game.server.api.net.NetAcceptHandler;
import luj.net.api.server.ConnectionAcceptInitializer;

final class ConnectionImpl implements NetAcceptHandler.Connection, NetAcceptHandler.Address {

  @Override
  public Integer id() {
    return _id;
  }

  @Override
  public String host() {
    return _bindAddr.host();
  }

  @Override
  public int port() {
    return _bindAddr.port();
  }

  Integer _id;

  ConnectionAcceptInitializer.Address _bindAddr;
}
