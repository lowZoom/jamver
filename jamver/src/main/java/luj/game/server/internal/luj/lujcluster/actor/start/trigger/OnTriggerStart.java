package luj.game.server.internal.luj.lujcluster.actor.start.trigger;

import luj.ava.spring.Internal;
import luj.game.server.internal.boot.game.StartListenTrigger;
import luj.game.server.internal.luj.lujcluster.actor.start.JamStartActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnTriggerStart implements JamStartActor.Handler<TriggerStartMsg> {

  @Override
  public void onHandle(Context ctx) {
    JamStartActor self = ctx.getActorState(this);
    TriggerStartMsg msg = ctx.getMessage(this);

    try {
      StartListenTrigger.get(self, msg.getStarterList()).trigger();

    } catch (Throwable e) {
      LOG.error(e.getMessage(), e);
      ctx.getLocalNode().shutdown();
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnTriggerStart.class);
}
