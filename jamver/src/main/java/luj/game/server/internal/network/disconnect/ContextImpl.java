package luj.game.server.internal.network.disconnect;

import luj.game.server.api.net.GameDisconnectHandler;

final class ContextImpl implements GameDisconnectHandler.Context {

  @Override
  public GameDisconnectHandler.Connection connection() {
    return _conn;
  }

  @Override
  public GameDisconnectHandler.Service service() {
    return _service;
  }

  ConnectionImpl _conn;

  ServiceImpl _service;
}
