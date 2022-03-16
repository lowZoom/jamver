package luj.game.server.internal.luj.lujcluster;

import luj.ava.spring.Internal;
import luj.cluster.api.node.member.NodeMemberHealthListener;
import luj.game.server.internal.luj.lujcluster.actor.cluster.health.ClusterUpdateHealthMsg;

@Internal
final class OnNodeHealth implements NodeMemberHealthListener {

  @Override
  public void onHealth(Context ctx) {
    RemoteNode remoteNode = ctx.getMemberNode();
    LocalNode localNode = ctx.getSelfNode();

    ClusterUpdateHealthMsg msg = new ClusterUpdateHealthMsg(
        remoteNode.getId(), remoteNode.getTags(), remoteNode.isHealthy());

    localNode.sendMessage(ClusterUpdateHealthMsg.class.getName(), msg);
  }

//  private static final Logger LOG = LoggerFactory.getLogger(OnNodeHealth.class);
}
