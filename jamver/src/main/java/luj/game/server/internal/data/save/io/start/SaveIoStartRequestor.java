package luj.game.server.internal.data.save.io.start;

import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSaveActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.io.start.SaveIoStartMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveIoStartRequestor {

  public SaveIoStartRequestor(DataSaveActor saveActor, Tellable saveRef) {
    _saveActor = saveActor;
    _saveRef = saveRef;
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.io.start.OnSaveIoStart#onHandle
   */
  public void request() {
    LOG.debug("启动新一轮数据落地");

    _saveActor.setIoRunning(true);
    _saveRef.tell(SaveIoStartMsg.SINGLETON);
  }

  private static final Logger LOG = LoggerFactory.getLogger(SaveIoStartRequestor.class);

  private final DataSaveActor _saveActor;
  private final Tellable _saveRef;
}
