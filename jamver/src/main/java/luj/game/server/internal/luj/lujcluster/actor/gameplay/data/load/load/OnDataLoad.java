package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.load;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.load.io.DataIoLoader;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadPlugin;

@Internal
final class OnDataLoad implements DataLoadActor.Handler<DataLoadMsg> {

  @Override
  public void onHandle(Context ctx) {
    DataLoadActor actor = ctx.getActorState(this);
    DataLoadMsg msg = ctx.getMessage(this);

    DataLoadPlugin plugin = actor.getLoadPlugin();

    new DataIoLoader(plugin.getDataLoader(), actor.getLoadState(),
        msg.getDataType(), msg.getDataId(), msg.getIdField()).load();
  }
}
