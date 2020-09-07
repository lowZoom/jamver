package luj.game.server.internal.luj.lujcluster.actor.cluster.start;

import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.StartRefMsg;

@Internal
final class OnStartRef implements ClusterCommActor.Handler<StartRefMsg> {

  @Override
  public void onHandle(Context ctx) {
    ClusterCommActor self = ctx.getActorState(this);
    StartRefMsg msg = ctx.getMessage(this);

    self.setSiblingRef(msg.getRefCollection());
    msg.getStartLatch().countDown();
  }
}
