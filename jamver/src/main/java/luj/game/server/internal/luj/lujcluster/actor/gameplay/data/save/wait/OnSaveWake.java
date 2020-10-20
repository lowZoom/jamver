package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.wait;

import static com.google.common.base.Preconditions.checkState;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.save.io.start.SaveIoStartRequestor;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;

@Internal
final class OnSaveWake implements DataSaveActor.Handler<SaveWakeMsg> {

  @Override
  public void onHandle(Context ctx) {
    DataSaveActor self = ctx.getActorState(this);
    checkState(!self.isIoRunning());

    IoWaitBatch batch = self.getWaitBatch();
    if (!isWaiting(batch)) {
      return;
    }

    Ref selfRef = ctx.getActorRef();
    new SaveIoStartRequestor(self, selfRef).request();
  }

  private boolean isWaiting(IoWaitBatch batch) {
    return !batch.getCreateList().isEmpty() || !batch.getUpdateMap().isEmpty();
  }
}
