package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.save.io.start.SaveIoStartRequestor;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.data.save.wait.add.IoWaitCreateAdder;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnDataCreate implements DataSaveActor.Handler<DataCreateMsg> {

  @Override
  public void onHandle(Context ctx) {
    DataSaveActor self = ctx.getActorState(this);
    DataCreateMsg msg = ctx.getMessage(this);

    LOG.debug("请求数据新建落地：{}#{}", msg.getDataType(), msg.getDataId());
    IoWaitBatch waitBatch = self.getWaitBatch();
    new IoWaitCreateAdder(waitBatch, msg).add();

    if (self.isIoRunning()) {
      int waitCount = waitBatch.getCreateList().size();
      LOG.debug("已有数据正在保存：#{}，进入等待批，数量：{}", self.getIoSeq(), waitCount);
      return;
    }

    Ref selfRef = ctx.getActorRef();
    new SaveIoStartRequestor(self, selfRef).request();
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnDataCreate.class);
}
