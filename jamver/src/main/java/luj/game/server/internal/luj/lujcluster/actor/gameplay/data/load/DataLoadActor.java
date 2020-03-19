package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;

public class DataLoadActor {

  public interface Handler<M> extends ActorMessageHandler<DataLoadActor, M> {
    // NOOP
  }

  public DataLoadActor(Object loadState, DataLoadPlugin loadPlugin,
      ActorPreStartHandler.Actor dataRef) {
    _loadState = loadState;
    _loadPlugin = loadPlugin;
    _dataRef = dataRef;
  }

  public Object getLoadState() {
    return _loadState;
  }

  public DataLoadPlugin getLoadPlugin() {
    return _loadPlugin;
  }

  public ActorPreStartHandler.Actor getDataRef() {
    return _dataRef;
  }

  private final Object _loadState;
  private final DataLoadPlugin _loadPlugin;

  private final ActorPreStartHandler.Actor _dataRef;
}
