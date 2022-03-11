package luj.game.server.internal.luj.lujcluster.actor.cluster.join;

import luj.cluster.api.node.member.NodeNewMemberListener;

public class ClusterJoinMsg {

  public ClusterJoinMsg(NodeNewMemberListener.NodeRemote memberNode) {
    _memberNode = memberNode;
  }

  public NodeNewMemberListener.NodeRemote getMemberNode() {
    return _memberNode;
  }

  private final NodeNewMemberListener.NodeRemote _memberNode;
}
