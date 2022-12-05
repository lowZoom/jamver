package luj.game.server.internal.network.proto.handle.invoke;

import luj.game.server.api.net.GameProtoHandler;

final class ContextImpl implements GameProtoHandler.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <P> P proto(GameProtoHandler<P> handler) {
    return (P) _proto;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> P param() {
    return (P) _param;
  }

  @Override
  public GameProtoHandler.Service service() {
    return _service;
  }

  Object _proto;
  Object _param;

  HandleServiceImpl _service;
}
