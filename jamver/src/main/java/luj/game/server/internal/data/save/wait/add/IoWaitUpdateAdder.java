package luj.game.server.internal.data.save.wait.add;

import java.util.HashMap;
import java.util.Map;
import luj.game.server.internal.data.save.wait.BatchUpdateItem;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;

public class IoWaitUpdateAdder {

  public IoWaitUpdateAdder(IoWaitBatch batch, DataUpdateMsg msg) {
    _batch = batch;
    _msg = msg;
  }

  public void add() {
    Map<Comparable<?>, BatchUpdateItem> batchMap = _batch.getUpdateMap();
    Comparable<?> dataId = _msg.getDataId();

    BatchUpdateItem oldItem = batchMap.get(dataId);
    if (oldItem != null) {
      mergeItem(oldItem);
      return;
    }

    BatchUpdateItem newItem = new BatchUpdateItem(dataId, _msg.getDataType(), _msg.getIdField(),
        new HashMap<>(_msg.getPrimitiveUpdated()),
        new HashMap<>(_msg.getSetUpdated()),
        new HashMap<>(_msg.getMapUpdated()));

    batchMap.put(dataId, newItem);
  }

  private void mergeItem(BatchUpdateItem item) {
    item.getPrimitiveUpdated().putAll(_msg.getPrimitiveUpdated());
    item.getSetUpdated().putAll(_msg.getSetUpdated());
    item.getMapUpdated().putAll(_msg.getMapUpdated());
  }

  private final IoWaitBatch _batch;

  private final DataUpdateMsg _msg;
}
