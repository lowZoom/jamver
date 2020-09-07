package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.start;

import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.StartRefMsg;

@Internal
final class OnStartRef implements GameplayDataActor.Handler<StartRefMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    StartRefMsg msg = ctx.getMessage(this);

    self.setSiblingRef(msg.getRefCollection());
    msg.getStartLatch().countDown();
  }
}
