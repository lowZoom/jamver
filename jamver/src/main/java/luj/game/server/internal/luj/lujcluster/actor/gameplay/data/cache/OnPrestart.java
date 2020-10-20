package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import luj.ava.spring.Internal;
import luj.game.server.api.plugin.JamverDataLoadInit;
import luj.game.server.api.plugin.JamverDataSaveInit;
import luj.game.server.internal.data.init.DataRootInitializer;
import luj.game.server.internal.data.load.io.init.DataLoadInitializer;
import luj.game.server.internal.data.save.init.DataSaveInitializer;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSavePlugin;

@Internal
final class OnPrestart implements GameplayDataActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    DataAllPlugin allPlugin = self.getAllPlugin();
    Object startParam = self.getStartParam();

    Object rootState = new DataRootInitializer(allPlugin.getRootInitPlugin(), startParam).init();
    self.setPluginState(rootState);

    Actor selfRef = ctx.getActor();
    Actor loadRef = ctx.createActor(loadActor(allPlugin.getLoadPlugin(), rootState, selfRef));
    self.setLoadRef(loadRef);

    Actor saveRef = ctx.createActor(saveActor(allPlugin.getSavePlugin(), rootState));
    self.setSaveRef(saveRef);
  }

  private DataLoadActor loadActor(DataLoadPlugin loadPlugin, Object pluginState, Actor dataRef) {
    JamverDataLoadInit initPlugin = loadPlugin.getLoadInit();
    Object loadState = new DataLoadInitializer(initPlugin, pluginState).init();

    return new DataLoadActor(loadState, loadPlugin, dataRef);
  }

  private DataSaveActor saveActor(DataSavePlugin savePlugin, Object pluginState) {
    JamverDataSaveInit initPlugin = savePlugin.getSaveInit();
    Object saveState = new DataSaveInitializer(initPlugin, pluginState).init();

    ExecutorService ioRunner = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
        .setNameFormat("data-save-io-%d")
        .build());

    IoWaitBatch waitBatch = new IoWaitBatch(new ArrayList<>(), new HashMap<>());
    return new DataSaveActor(saveState, savePlugin, ioRunner, waitBatch);
  }
}
