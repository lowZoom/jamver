package luj.game.server.internal.network.disconnect;

import luj.game.server.api.net.GameDisconnectHandler;

final class ConnectionImpl implements GameDisconnectHandler.Connection {

  @Override
  public Integer id() {
    return _id;
  }

  Integer _id;
}
