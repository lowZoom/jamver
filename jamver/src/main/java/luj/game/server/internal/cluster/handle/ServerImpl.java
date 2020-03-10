package luj.game.server.internal.cluster.handle;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.cluster.ServerMessageHandler;

final class ServerImpl implements ServerMessageHandler.Server {

  ServerImpl(ActorMessageHandler.Node remoteNode) {
    _remoteNode = remoteNode;
  }

  @Override
  public void sendMessage(Object msg) {
    //FIXME: 这里应该发ClusterReceiveMsg
    _remoteNode.sendMessage(msg);
  }

  @Override
  public String toString() {
    return _remoteNode.toString();
  }

  private final ActorMessageHandler.Node _remoteNode;
}