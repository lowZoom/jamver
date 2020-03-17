package luj.game.server.internal.data.load.io.init;

import luj.game.server.api.plugin.JamverDataLoadInit;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.dataload.DataLoadActor;

public class DataIoInitializer {

  public DataIoInitializer(DataLoadActor actorState, JamverDataLoadInit initPlugin) {
    _actorState = actorState;
    _initPlugin = initPlugin;
  }

  public void init() {
    InitContextImpl ctx = new InitContextImpl();

    Object loadState = _initPlugin.onInit(ctx);
    _actorState.setLoadState(loadState);
  }

  private final DataLoadActor _actorState;

  private final JamverDataLoadInit _initPlugin;
}
