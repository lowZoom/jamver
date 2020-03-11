package luj.game.server.internal.cluster.handle;

import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.cluster.ServerMessageHandler.Server;

final class HandleContextImpl implements ServerMessageHandler.Context {

  HandleContextImpl(Object message, ServerMessageHandler.Server remoteServer,
      ServerMessageHandler.Service service) {
    _message = message;
    _remoteServer = remoteServer;
    _service = service;
  }

  @Override
  public <M> M getMessage(ServerMessageHandler<M> handler) {
    return message(handler);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <M> M message(ServerMessageHandler<M> handler) {
    return (M) _message;
  }

  @Override
  public ServerMessageHandler.Server getRemoteServer() {
    return remoteServer();
  }

  @Override
  public Server remoteServer() {
    return _remoteServer;
  }

  @Override
  public ServerMessageHandler.Service service() {
    return _service;
  }

  private final Object _message;
  private final ServerMessageHandler.Server _remoteServer;

  private final ServerMessageHandler.Service _service;
}
