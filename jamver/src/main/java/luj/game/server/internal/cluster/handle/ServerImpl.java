package luj.game.server.internal.cluster.handle;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.cluster.receive.ClusterReceiveMsg;

final class ServerImpl implements ServerMessageHandler.Server {

  ServerImpl(ActorMessageHandler.Node remoteNode) {
    _remoteNode = remoteNode;
  }

  @Override
  public void sendMessage(Object msg) {
    ClusterReceiveMsg gameMsg = new ClusterReceiveMsg(msg.getClass().getName(), msg);
    _remoteNode.sendMessage(ClusterReceiveMsg.class.getName(), gameMsg);
  }

  @Override
  public String toString() {
    return _remoteNode.toString();
  }

  private final ActorMessageHandler.Node _remoteNode;
}
