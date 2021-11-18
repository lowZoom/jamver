package luj.game.server.internal.data.init;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.plugin.JamverDataLoadInit;
import luj.game.server.api.plugin.JamverDataRootInit;
import luj.game.server.api.plugin.JamverDataSaveInit;
import luj.game.server.internal.data.id.init.DataIdInitializer;
import luj.game.server.internal.data.init.root.DataRootInitInvoker;
import luj.game.server.internal.data.load.io.init.DataLoadInitializer;
import luj.game.server.internal.data.save.init.DataSaveInitializer;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.DataAllPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSavePlugin;
import org.slf4j.Logger;

public class DataActorStarter {

  public DataActorStarter(GameplayDataActor self, ActorPreStartHandler.Context context,
      Logger log) {
    _self = self;
    _context = context;
    _log = log;
  }

  public void start() {
    DataAllPlugin allPlugin = _self.getAllPlugin();

    JamverDataRootInit initPlugin = allPlugin.getRootInitPlugin();
    boolean skipInit = initPlugin == null;

    Object rootState = initRoot(_self, skipInit);
    _self.setPluginState(rootState);

    ActorPreStartHandler.Actor selfRef = _context.getActor();
    _self.setLoadRef(_context.createActor(loadActor(
        allPlugin.getLoadPlugin(), rootState, selfRef, skipInit)));

    ActorPreStartHandler.Actor saveRef = _context.createActor(
        saveActor(allPlugin.getSavePlugin(), rootState, skipInit));
    _self.setSaveRef(saveRef);
  }

  private Object initRoot(GameplayDataActor self, boolean skipInit) {
    if (skipInit) {
      _log.debug("[game]未发现数据模块初始化，跳过");
      return null;
    }

    new DataIdInitializer(_self).init();

    return new DataRootInitInvoker(self.getAllPlugin()
        .getRootInitPlugin(), self.getStartParam()).invoke();
  }

  private DataLoadActor loadActor(DataLoadPlugin loadPlugin,
      Object pluginState, ActorPreStartHandler.Actor dataRef, boolean skipInit) {
    JamverDataLoadInit initPlugin = loadPlugin.getLoadInit();
    Object loadState = skipInit ? null : new DataLoadInitializer(initPlugin, pluginState).init();

    int cores = Math.min(Runtime.getRuntime().availableProcessors(), 10);
    ExecutorService ioRunner = Executors.newFixedThreadPool(cores, new ThreadFactoryBuilder()
        .setNameFormat("data-load-io-%d")
        .build());

    return new DataLoadActor(loadState, loadPlugin, ioRunner, dataRef);
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

  private final GameplayDataActor _self;

  private final ActorPreStartHandler.Context _context;
  private final Logger _log;
}
