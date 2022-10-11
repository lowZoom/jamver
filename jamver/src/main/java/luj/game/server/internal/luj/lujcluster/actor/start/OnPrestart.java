package luj.game.server.internal.luj.lujcluster.actor.start;

import com.google.common.collect.ImmutableList;
import luj.ava.spring.Internal;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.internal.luj.lujcluster.actor.start.trigger.TriggerStartMsg;

@Internal
final class OnPrestart implements JamStartActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    JamStartActor self = ctx.getActorState(this);

    ctx.getActor().tell(new TriggerStartMsg(ImmutableList.<GameStartListener>builder()
        .addAll(self.getStartParam().getStartListenerList())
        .addAll(self.getDynamicStart())
        .build()));
  }
}
