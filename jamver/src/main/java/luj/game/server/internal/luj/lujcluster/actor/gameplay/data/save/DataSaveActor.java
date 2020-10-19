package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save;

import java.util.concurrent.ExecutorService;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.internal.data.save.wait.IoWaitBatch;

public class DataSaveActor {

  public interface Handler<M> extends ActorMessageHandler<DataSaveActor, M> {
    // NOOP
  }

  public DataSaveActor(Object saveState, DataSavePlugin savePlugin, ExecutorService ioRunner,
      IoWaitBatch waitBatch) {
    _saveState = saveState;
    _savePlugin = savePlugin;
    _ioRunner = ioRunner;
    _waitBatch = waitBatch;
  }

  public boolean isIoRunning() {
    return _ioRunning;
  }

  public void setIoRunning(boolean ioRunning) {
    _ioRunning = ioRunning;
  }

  public Object getSaveState() {
    return _saveState;
  }

  public DataSavePlugin getSavePlugin() {
    return _savePlugin;
  }

  public ExecutorService getIoRunner() {
    return _ioRunner;
  }

  public IoWaitBatch getWaitBatch() {
    return _waitBatch;
  }

  private boolean _ioRunning;

  private final Object _saveState;
  private final DataSavePlugin _savePlugin;

  private final ExecutorService _ioRunner;
  private final IoWaitBatch _waitBatch;
}
