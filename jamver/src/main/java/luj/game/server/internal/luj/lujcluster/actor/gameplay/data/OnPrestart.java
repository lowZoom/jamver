package luj.game.server.internal.luj.lujcluster.actor.gameplay.data;

import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.dataload.DataLoadActor;

@Internal
final class OnPrestart implements GameplayDataActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor actor = ctx.getActorState(this);

    Actor loadRef = ctx.createActor(loadActor(actor));
    actor.setLoadRef(loadRef);
  }

  private DataLoadActor loadActor(GameplayDataActor dataActor) {
    return new DataLoadActor(dataActor.getLoadPlugin());
  }
}
