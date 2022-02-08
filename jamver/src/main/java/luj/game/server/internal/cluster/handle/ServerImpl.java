package luj.game.server.internal.cluster.handle;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.Tellable;
import luj.cluster.api.node.NodeNewMemberListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.cluster.send.ClusterSendMsg;

final class ServerImpl implements ServerMessageHandler.Server {

  @Override
  public void sendMessage(Object msg) {
    String msgPath = msg.getClass().getName();
    _clusterRef.tell(new ClusterSendMsg(msgPath, msg, (NodeNewMemberListener.Node) _remoteNode));
  }

  @Override
  public String toString() {
    return "Server[" + _remoteNode.getIp() + "]";
  }

  ActorMessageHandler.Node _remoteNode;

  Tellable _clusterRef;
}
