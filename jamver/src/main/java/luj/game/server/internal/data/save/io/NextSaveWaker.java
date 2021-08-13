package luj.game.server.internal.data.save.io;

import static com.google.common.base.Preconditions.checkState;

import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.save.io.start.SaveIoStartRequestor;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;

public class NextSaveWaker {

  public NextSaveWaker(DataSaveActor saveActor, Tellable saveRef) {
    _saveActor = saveActor;
    _saveRef = saveRef;
  }

  public void wake() {
    DataSaveActor self = _saveActor;
    checkState(!self.isIoRunning(), "#%s", self.getIoSeq());

    IoWaitBatch batch = self.getWaitBatch();
    if (!isWaiting(batch)) {
      return;
    }

    new SaveIoStartRequestor(self, _saveRef).request();
  }

  private boolean isWaiting(IoWaitBatch batch) {
    return !batch.getCreateList().isEmpty() || !batch.getUpdateMap().isEmpty();
  }

  private final DataSaveActor _saveActor;

  private final Tellable _saveRef;
}
