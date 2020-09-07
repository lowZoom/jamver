package luj.game.server.internal.luj.lujcluster;

import luj.ava.spring.Internal;
import luj.cluster.api.node.NodeNewMemberListener;
import luj.game.server.internal.cluster.join.ClusterJoinFirer;
import luj.game.server.internal.cluster.join.register.handle.ClusterHandleRegisterSender;

@Internal
final class OnNodeJoin implements NodeNewMemberListener {

  @Override
  public void onMember(Context ctx) throws Exception {
    JambeanInLujcluster jambean = ctx.getApplicationBean();

    Node remoteNode = ctx.getMemberNode();
    new ClusterHandleRegisterSender(jambean.getClusterMessageList(), remoteNode).send();

    new ClusterJoinFirer(jambean.getClusterJoinList(), remoteNode,
        jambean.getClusterProtoPlugin().getProtoEncode()).fire();
  }
}
