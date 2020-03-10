package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.execute;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.execute.DataCmdExecutor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.GameplayDataActor;

@Internal
final class OnDatacmdExec implements GameplayDataActor.Handler<DatacmdExecMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor actor = ctx.getActorState(this);
    DatacmdExecMsg msg = ctx.getMessage(this);

    Class<?> cmdType = msg.getCmdType();
    new DataCmdExecutor(actor.getCommandMap(), cmdType).execute();
  }
}
