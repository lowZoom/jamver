package luj.game.server.internal.luj.lujcluster.actor.gameplay.dataload;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.load.io.init.DataIoInitializer;

@Internal
final class OnPrestart implements DataLoadActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    DataLoadActor actorState = ctx.getActorState(this);

    DataLoadPlugin plugin = actorState.getLoadPlugin();
    new DataIoInitializer(actorState, plugin.getLoadInit()).init();
  }
}
