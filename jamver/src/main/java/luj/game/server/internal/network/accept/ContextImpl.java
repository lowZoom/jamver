package luj.game.server.internal.network.accept;

import luj.game.server.api.net.NetAcceptHandler;

final class ContextImpl implements NetAcceptHandler.Context {

  @Override
  public NetAcceptHandler.Connection connection() {
    return _conn;
  }

  @Override
  public NetAcceptHandler.Address getBindAddress() {
    return _conn;
  }

  @Override
  public NetAcceptHandler.Service service() {
    return _service;
  }

  ConnectionImpl _conn;

  ServiceImpl _service;
}
