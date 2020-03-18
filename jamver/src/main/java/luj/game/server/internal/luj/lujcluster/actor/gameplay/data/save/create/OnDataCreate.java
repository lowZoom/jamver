package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.save.create.DataIoCreator;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSavePlugin;

@Internal
final class OnDataCreate implements DataSaveActor.Handler<DataCreateMsg> {

  @Override
  public void onHandle(Context ctx) {
    DataCreateMsg msg = ctx.getMessage(this);
    DataSaveActor actorState = ctx.getActorState(this);

    DataSavePlugin plugin = actorState.getSavePlugin();
    new DataIoCreator(plugin.getSaveCreate(),
        actorState.getSaveState(), msg.getDataType(), msg.getDataMap()).create();
  }

//  private static final Logger LOG = LoggerFactory.getLogger(OnDataCreate.class);
}
