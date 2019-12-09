package luj.game.server.internal.luj.lujcluster.actor.cluster.receive;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.cluster.ServerMessageHandler;

final class ServerImpl implements ServerMessageHandler.Server {

  ServerImpl(ActorMessageHandler.Node remoteNode) {
    _remoteNode = remoteNode;
  }

  @Override
  public void sendMessage(Object msg) {
    _remoteNode.sendMessage(msg);
  }

  private final ActorMessageHandler.Node _remoteNode;
}
