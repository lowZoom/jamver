package luj.game.server.internal.network.accept;

import luj.game.server.api.net.GameAcceptHandler;

final class ContextImpl implements GameAcceptHandler.Context {

  @Override
  public GameAcceptHandler.Connection connection() {
    return _conn;
  }

  @Override
  public GameAcceptHandler.Address getBindAddress() {
    return _conn;
  }

  @Override
  public GameAcceptHandler.Service service() {
    return _service;
  }

  ConnectionImpl _conn;

  ServiceImpl _service;
}
