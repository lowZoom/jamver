package luj.game.server.internal.network.accept;

import luj.game.server.api.net.GameAcceptHandler;
import luj.net.api.server.ConnectionAcceptInitializer;

final class ContextImpl implements GameAcceptHandler.Context {

  @Override
  public GameAcceptHandler.Connection connection() {
    return _conn;
  }

  @Override
  public GameAcceptHandler.Address getBindAddress() {
    AddressImpl addr = new AddressImpl();
    addr._addr = _bindAddr;
    return addr;
  }

  @Override
  public GameAcceptHandler.Service service() {
    return _service;
  }

  ConnectionImpl _conn;
  ConnectionAcceptInitializer.Address _bindAddr;

  ServiceImpl _service;
}
