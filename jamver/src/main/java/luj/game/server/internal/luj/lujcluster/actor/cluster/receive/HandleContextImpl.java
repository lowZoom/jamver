package luj.game.server.internal.luj.lujcluster.actor.cluster.receive;

import luj.game.server.api.cluster.ServerMessageHandler;

final class HandleContextImpl implements ServerMessageHandler.Context {

  HandleContextImpl(Object message, ServerMessageHandler.Server remoteServer) {
    _message = message;
    _remoteServer = remoteServer;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <M> M getMessage(ServerMessageHandler<M> handler) {
    return (M) _message;
  }

  @Override
  public ServerMessageHandler.Server getRemoteServer() {
    return _remoteServer;
  }

  private final Object _message;

  private final ServerMessageHandler.Server _remoteServer;
}
