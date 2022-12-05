package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.io.start;

import static com.google.common.base.Preconditions.checkState;

import java.util.concurrent.ExecutorService;
import luj.game.server.internal.data.save.io.invoke.DataIoInvoker;
import luj.game.server.internal.data.save.wait.consume.DataIoWaitConsumer;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.io.finish.SaveIoFinishMsg;
import luj.spring.anno.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnSaveIoStart implements DataSaveActor.Handler<SaveIoStartMsg> {

  @Override
  public void onHandle(Context ctx) {
    DataSaveActor self = ctx.getActorState(this);
    Ref selfRef = ctx.getActorRef();
    checkState(self.isIoRunning());

    DataIoWaitConsumer.Result result = new DataIoWaitConsumer(self.getWaitBatch()).consume();
    checkState(!result.created().isEmpty() || !result.updated().isEmpty());

    ExecutorService ioRunner = self.getIoRunner();
    ioRunner.submit(() -> runIo(self, selfRef, result));
  }

  private void runIo(DataSaveActor self, Ref selfRef, DataIoWaitConsumer.Result content) {
    LOG.debug("触发数据库IO");

    try {
      new DataIoInvoker(self.getSavePlugin().getSaveIo(),
          self.getSaveState(), content.created(), content.updated()).invoke();
    } catch (Throwable e) {
      LOG.error(e.getMessage(), e);
      //TODO: 考虑出错重试
    }

    selfRef.tell(SaveIoFinishMsg.SINGLETON);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnSaveIoStart.class);
}
