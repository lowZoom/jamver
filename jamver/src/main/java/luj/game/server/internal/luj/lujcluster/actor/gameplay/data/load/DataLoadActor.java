package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load;

import luj.cluster.api.actor.ActorMessageHandler;

public class DataLoadActor {

  public interface Handler<M> extends ActorMessageHandler<DataLoadActor, M> {
    // NOOP
  }

  public DataLoadActor(Object loadState, DataLoadPlugin loadPlugin) {
    _loadState = loadState;
    _loadPlugin = loadPlugin;
  }

  public Object getLoadState() {
    return _loadState;
  }

  public DataLoadPlugin getLoadPlugin() {
    return _loadPlugin;
  }

  private final Object _loadState;

  private final DataLoadPlugin _loadPlugin;
}
