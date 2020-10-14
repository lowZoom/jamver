package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.save.update.DataIoUpdater;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSavePlugin;

@Internal
final class OnDataUpdate implements DataSaveActor.Handler<DataUpdateMsg> {

  @Override
  public void onHandle(Context ctx) {
    DataUpdateMsg msg = ctx.getMessage(this);
    DataSaveActor actor = ctx.getActorState(this);

    DataSavePlugin plugin = actor.getSavePlugin();
    new DataIoUpdater(plugin.getSaveUpdate(), actor.getSaveState(), msg.getDataType(),
        msg.getPrimitiveUpdated(), msg.getSetUpdated(), msg.getMapUpdated(), msg.getDataId(),
        msg.getIdField()).update();
  }
}
