package luj.game.server.internal.luj.lujcluster;

import luj.ava.spring.Internal;
import luj.cluster.api.node.member.NodeNewMemberListener;
import luj.game.server.internal.cluster.join.register.handle.ClusterHandleRegisterSender;
import luj.game.server.internal.luj.lujcluster.actor.cluster.join.ClusterJoinMsg;

@Internal
final class OnNodeJoin implements NodeNewMemberListener {

  @Override
  public void onMember(Context ctx) {
    JambeanInLujcluster jambean = ctx.getApplicationBean();
    NodeRemote remoteNode = ctx.getMemberNode();
    new ClusterHandleRegisterSender(jambean.getClusterMsgHandleList(), remoteNode).send();

    Node localNode = ctx.getSelfNode();
    ClusterJoinMsg msg = new ClusterJoinMsg(remoteNode);
    localNode.sendMessage(ClusterJoinMsg.class.getName(), msg);
  }
}
