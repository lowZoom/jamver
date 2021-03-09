package luj.game.server.internal.network.proto.handle.invoke;

import luj.game.server.api.net.GameProtoHandler;

final class ContextImpl implements GameProtoHandler.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <P> P proto(GameProtoHandler<P> handler) {
    return (P) _proto;
  }

  @Override
  public Integer connectionId() {
    return _connId;
  }

  @Override
  public GameProtoHandler.Player player() {
    throw new UnsupportedOperationException("player已废弃");
  }

  @Override
  public GameProtoHandler.Service service() {
    return _service;
  }

  Object _proto;
  Integer _connId;

  HandleServiceImpl _service;
}
