package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.init.DataActorStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnPrestart implements GameplayDataActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);

    new DataActorStarter(self, ctx, LOG).start();
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnPrestart.class);
}
