package luj.game.server.internal.luj.lujcluster.actor.gameplay.dataload;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;

public class DataLoadActor {

  public interface Handler<M> extends ActorMessageHandler<DataLoadActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<DataLoadActor> {
    // NOOP
  }

  public DataLoadActor(DataLoadPlugin loadPlugin) {
    _loadPlugin = loadPlugin;
  }

  public Object getLoadState() {
    return _loadState;
  }

  public void setLoadState(Object loadState) {
    _loadState = loadState;
  }

  public DataLoadPlugin getLoadPlugin() {
    return _loadPlugin;
  }

  private Object _loadState;

  private final DataLoadPlugin _loadPlugin;
}
