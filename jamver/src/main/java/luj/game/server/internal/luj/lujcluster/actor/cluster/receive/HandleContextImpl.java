package luj.game.server.internal.luj.lujcluster.actor.cluster.receive;

import luj.game.server.api.cluster.ServerMessageHandler;

final class HandleContextImpl implements ServerMessageHandler.Context {

  HandleContextImpl(Object message) {
    _message = message;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <M> M getMessage(ServerMessageHandler<M> handler) {
    return (M) _message;
  }

  private final Object _message;
}
