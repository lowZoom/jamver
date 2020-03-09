package luj.game.server.internal.cluster.join;

import luj.cluster.api.node.NodeNewMemberListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.internal.luj.lujcluster.actor.cluster.receive.ClusterReceiveMsg;

final class ServerImpl implements ServerJoinListener.Server {

  ServerImpl(NodeNewMemberListener.Node remoteNode) {
    _remoteNode = remoteNode;
  }

  @Override
  public void sendMessage(Object msg) {
    ClusterReceiveMsg gameMsg = new ClusterReceiveMsg(msg.getClass().getName(), msg);
    _remoteNode.sendMessage(ClusterReceiveMsg.class.getName(), gameMsg);
  }

  private final NodeNewMemberListener.Node _remoteNode;
}
