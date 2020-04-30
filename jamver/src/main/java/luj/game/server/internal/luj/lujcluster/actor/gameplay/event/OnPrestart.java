package luj.game.server.internal.luj.lujcluster.actor.gameplay.event;

//@Internal
final class OnPrestart implements GameplayEventActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    GameplayEventActor self = ctx.getActorState(this);

    //TODO: 向dataRef注册自己
  }
}
