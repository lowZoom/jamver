package luj.game.server.internal.luj.lujcluster.actor.gameplay.event.start;

import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.StartRefMsg;

@Internal
final class OnStartRef implements GameplayEventActor.Handler<StartRefMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayEventActor self = ctx.getActorState(this);
    StartRefMsg msg = ctx.getMessage(this);

    self.setSiblingRef(msg.getRefCollection());
    msg.getStartLatch().countDown();
  }
}
