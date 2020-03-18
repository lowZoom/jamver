package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save;

import luj.cluster.api.actor.ActorMessageHandler;

public class DataSaveActor {

  public interface Handler<M> extends ActorMessageHandler<DataSaveActor, M> {
    // NOOP
  }

  public DataSaveActor(Object saveState, DataSavePlugin savePlugin) {
    _saveState = saveState;
    _savePlugin = savePlugin;
  }

  public Object getSaveState() {
    return _saveState;
  }

  public DataSavePlugin getSavePlugin() {
    return _savePlugin;
  }

  private final Object _saveState;

  private final DataSavePlugin _savePlugin;
}
