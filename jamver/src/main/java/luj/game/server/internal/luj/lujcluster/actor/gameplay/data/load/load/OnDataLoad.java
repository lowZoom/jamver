package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.load;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.load.io.DataIoLoader;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadPlugin;

/**
 * 实际产生IO的地方
 */
@Internal
final class OnDataLoad implements DataLoadActor.Handler<DataLoadMsg> {

  @Override
  public void onHandle(Context ctx) {
    DataLoadActor self = ctx.getActorState(this);
    DataLoadMsg msg = ctx.getMessage(this);

    DataLoadPlugin plugin = self.getLoadPlugin();
    new DataIoLoader(plugin.getDataLoader(), self.getLoadState(),
        msg.getDataType(), msg.getDataId(), msg.getIdField(), self.getDataRef()).load();
  }
}
