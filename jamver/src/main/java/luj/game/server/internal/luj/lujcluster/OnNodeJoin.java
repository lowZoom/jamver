package luj.game.server.internal.luj.lujcluster;

import luj.ava.spring.Internal;
import luj.cluster.api.node.NodeNewMemberListener;
import luj.game.server.internal.cluster.join.ClusterJoinFirer;

@Internal
final class OnNodeJoin implements NodeNewMemberListener {

  @Override
  public void onMember(Context ctx) throws Exception {
    JambeanInLujcluster jambean = ctx.getApplicationBean();

    new ClusterJoinFirer(jambean.getClusterJoinList(), ctx.getMemberNode(),
        jambean.getClusterProtoPlugin().getProtoEncode()).fire();
  }
}
