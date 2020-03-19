package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache;

import luj.ava.spring.Internal;
import luj.game.server.api.plugin.JamverDataLoadInit;
import luj.game.server.api.plugin.JamverDataSaveInit;
import luj.game.server.internal.data.init.DataRootInitializer;
import luj.game.server.internal.data.load.io.init.DataLoadInitializer;
import luj.game.server.internal.data.save.init.DataSaveInitializer;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;

@Internal
final class OnPrestart implements GameplayDataActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    Actor selfRef = ctx.getActor();

    Object rootState = new DataRootInitializer(self.getInitPlugin()).init();
    self.setPluginState(rootState);

    Actor loadRef = ctx.createActor(loadActor(self, rootState, selfRef));
    self.setLoadRef(loadRef);

    Actor saveRef = ctx.createActor(saveActor(self, rootState));
    self.setSaveRef(saveRef);
  }

  private DataLoadActor loadActor(GameplayDataActor dataActor, Object pluginState, Actor dataRef) {
    JamverDataLoadInit initPlugin = dataActor.getLoadPlugin().getLoadInit();
    Object loadState = new DataLoadInitializer(initPlugin, pluginState).init();

    return new DataLoadActor(loadState, dataActor.getLoadPlugin(), dataRef);
  }

  private DataSaveActor saveActor(GameplayDataActor dataActor, Object pluginState) {
    JamverDataSaveInit initPlugin = dataActor.getSavePlugin().getSaveInit();
    Object saveState = new DataSaveInitializer(initPlugin, pluginState).init();

    return new DataSaveActor(saveState, dataActor.getSavePlugin());
  }
}
