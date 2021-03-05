package luj.game.server.internal.luj.lujcluster.actor.network.start;

import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.StartRefMsg;

@Internal
final class OnStartRef implements NetRootActor.Handler<StartRefMsg> {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);
    StartRefMsg msg = ctx.getMessage(this);

    self.setSiblingRef(msg.getRefCollection());
    msg.getStartLatch().countDown();
  }
}
