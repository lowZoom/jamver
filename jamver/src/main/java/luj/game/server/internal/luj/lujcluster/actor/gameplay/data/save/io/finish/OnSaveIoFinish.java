package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.io.finish;

import static com.google.common.base.Preconditions.checkState;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.save.io.NextSaveWaker;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnSaveIoFinish implements DataSaveActor.Handler<SaveIoFinishMsg> {

  @Override
  public void onHandle(Context ctx) {
    DataSaveActor self = ctx.getActorState(this);
    checkState(self.isIoRunning());

    LOG.debug("落地结束：#{}", self.getIoSeq());
    self.setIoRunning(false);

    Ref selfRef = ctx.getActorRef();
    new NextSaveWaker(self, selfRef).wake();
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnSaveIoFinish.class);
}
