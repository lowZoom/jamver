package luj.game.server.internal.data.save.wait.add;

import luj.game.server.internal.data.save.wait.BatchCreateItem;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create.DataCreateMsg;

public class IoWaitCreateAdder {

  public IoWaitCreateAdder(IoWaitBatch batch, DataCreateMsg msg) {
    _batch = batch;
    _msg = msg;
  }

  public void add() {
    BatchCreateItem item = new BatchCreateItem(_msg.getDataType(), _msg.getDataMap());
    _batch.getCreateList().add(item);
  }

  private final IoWaitBatch _batch;

  private final DataCreateMsg _msg;
}
