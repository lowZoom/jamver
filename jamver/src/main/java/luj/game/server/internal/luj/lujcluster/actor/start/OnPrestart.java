package luj.game.server.internal.luj.lujcluster.actor.start;

import luj.ava.spring.Internal;
import luj.game.server.internal.boot.game.StartTrigger;

@Internal
final class OnPrestart implements JamStartActor.PreStart {

  @Override
  public void onHandle(Context ctx) throws Exception {
    JamStartActor self = ctx.getActorState(this);
    self.getStartLatch().await();

    new StartTrigger(self.getStartParam().getStartListenerList(),
        self.getRefCollection().getDataRef()).trigger();
  }
}
