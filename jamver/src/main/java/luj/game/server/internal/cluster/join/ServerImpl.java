package luj.game.server.internal.cluster.join;

import java.util.Set;
import luj.cluster.api.actor.Tellable;
import luj.cluster.api.node.NodeNewMemberListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.internal.luj.lujcluster.actor.cluster.send.ClusterSendMsg;

final class ServerImpl implements ServerJoinListener.Server {

  @Override
  public Set<String> tags() {
    return _remoteNode.getTags();
  }

  @Override
  public void sendMessage(Object msg) {
    String msgPath = msg.getClass().getName();
    _clusterRef.tell(new ClusterSendMsg(msgPath, msg, _remoteNode));
  }

  NodeNewMemberListener.NodeRemote _remoteNode;

  Tellable _clusterRef;
}
