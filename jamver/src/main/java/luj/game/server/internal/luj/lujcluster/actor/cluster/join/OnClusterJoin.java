package luj.game.server.internal.luj.lujcluster.actor.cluster.join;

import luj.ava.spring.Internal;
import luj.game.server.internal.cluster.join.ClusterJoinFirer;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterProtoPlugin;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

@Internal
final class OnClusterJoin implements ClusterCommActor.Handler<ClusterJoinMsg> {

  @Override
  public void onHandle(Context ctx) throws Exception {
    ClusterCommActor self = ctx.getActorState(this);
    ClusterJoinMsg msg = ctx.getMessage(this);

    Ref selfRef = ctx.getActorRef();
    TopLevelRefs siblingRef = self.getSiblingRef();

    new ClusterJoinFirer(self.getJoinListeners(), msg.getMemberNode(), selfRef,
        self.getCommandMap(), self.getLujbean(), siblingRef.getDataRef()).fire();
  }
}
