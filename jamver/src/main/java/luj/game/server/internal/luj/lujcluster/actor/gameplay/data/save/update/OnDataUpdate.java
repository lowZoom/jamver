package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update;

import luj.ava.spring.Internal;
import luj.game.server.internal.data.save.io.start.SaveIoStartRequestor;
import luj.game.server.internal.data.save.wait.add.IoWaitUpdateAdder;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnDataUpdate implements DataSaveActor.Handler<DataUpdateMsg> {

  @Override
  public void onHandle(Context ctx) {
    DataSaveActor self = ctx.getActorState(this);
    DataUpdateMsg msg = ctx.getMessage(this);

    LOG.debug("请求数据更新落地：{}#{}", msg.getDataType(), msg.getDataId());
    new IoWaitUpdateAdder(self.getWaitBatch(), msg).add();

    if (self.isIoRunning()) {
      LOG.debug("已有数据正在保存，进入等待批，数量：{}", self.getWaitBatch().getUpdateMap().size());
      return;
    }

    Ref selfRef = ctx.getActorRef();
    new SaveIoStartRequestor(self, selfRef).request();
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnDataUpdate.class);
}
