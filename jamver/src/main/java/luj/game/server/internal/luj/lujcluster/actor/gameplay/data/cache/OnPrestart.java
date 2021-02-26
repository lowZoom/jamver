package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import luj.ava.spring.Internal;
import luj.game.server.api.plugin.JamverDataLoadInit;
import luj.game.server.api.plugin.JamverDataRootInit;
import luj.game.server.api.plugin.JamverDataSaveInit;
import luj.game.server.internal.data.init.DataRootInitializer;
import luj.game.server.internal.data.load.io.init.DataLoadInitializer;
import luj.game.server.internal.data.save.init.DataSaveInitializer;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSavePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnPrestart implements GameplayDataActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    DataAllPlugin allPlugin = self.getAllPlugin();

    JamverDataRootInit initPlugin = allPlugin.getRootInitPlugin();
    boolean skipInit = initPlugin == null;

    Object rootState = initRoot(self, skipInit);
    self.setPluginState(rootState);

    Actor selfRef = ctx.getActor();
    self.setLoadRef(ctx.createActor(loadActor(
        allPlugin.getLoadPlugin(), rootState, selfRef, skipInit)));

    Actor saveRef = ctx.createActor(saveActor(allPlugin.getSavePlugin(), rootState, skipInit));
    self.setSaveRef(saveRef);
  }

  private Object initRoot(GameplayDataActor self, boolean skipInit) {
    if (skipInit) {
      LOG.debug("未发现数据模块初始化，跳过");
      return null;
    }
    return new DataRootInitializer(self.getAllPlugin()
        .getRootInitPlugin(), self.getStartParam()).init();
  }

  private DataLoadActor loadActor(DataLoadPlugin loadPlugin,
      Object pluginState, Actor dataRef, boolean skipInit) {
    JamverDataLoadInit initPlugin = loadPlugin.getLoadInit();
    Object loadState = skipInit ? null : new DataLoadInitializer(initPlugin, pluginState).init();

    return new DataLoadActor(loadState, loadPlugin, dataRef);
  }

  private DataSaveActor saveActor(DataSavePlugin savePlugin, Object pluginState, boolean skipInit) {
    JamverDataSaveInit initPlugin = savePlugin.getSaveInit();
    Object saveState = skipInit ? null : new DataSaveInitializer(initPlugin, pluginState).init();

    ExecutorService ioRunner = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
        .setNameFormat("data-save-io-%d")
        .build());

    IoWaitBatch waitBatch = new IoWaitBatch(new ArrayList<>(), new HashMap<>());
    return new DataSaveActor(saveState, savePlugin, ioRunner, waitBatch);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnPrestart.class);
}
