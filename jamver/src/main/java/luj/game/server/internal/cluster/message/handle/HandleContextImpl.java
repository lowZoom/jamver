package luj.game.server.internal.cluster.message.handle;

import luj.game.server.api.cluster.ServerMessageHandler;

final class HandleContextImpl implements ServerMessageHandler.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <M> M message(ServerMessageHandler<M> handler) {
    return (M) _message;
  }

  @Override
  public ServerMessageHandler.Server remoteServer() {
    return _remoteServer;
  }

  @Override
  public ServerMessageHandler.Service service() {
    return _service;
  }

  Object _message;
  ServerMessageHandler.Server _remoteServer;

  ServerMessageHandler.Service _service;
}
