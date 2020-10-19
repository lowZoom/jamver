package luj.game.server.internal.data.save.wait.consume;

import java.util.List;
import java.util.stream.Collectors;
import luj.game.server.internal.data.save.wait.BatchCreateItem;
import luj.game.server.internal.data.save.wait.BatchUpdateItem;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create.DataCreateMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;

public class DataIoWaitConsumer {

  public interface Result {

    List<DataCreateMsg> created();

    List<DataUpdateMsg> updated();
  }

  public DataIoWaitConsumer(IoWaitBatch batch) {
    _batch = batch;
  }

  public Result consume() {
    List<DataCreateMsg> createList = _batch.getCreateMap().values().stream()
        .map(this::encodeCreate)
        .collect(Collectors.toList());

    List<DataUpdateMsg> updateList = _batch.getUpdateMap().values().stream()
        .map(this::encodeUpdate)
        .collect(Collectors.toList());

    ResultImpl result = new ResultImpl();
    result._createList = createList;
    result._updateList = updateList;

    clearBatch();
    return result;
  }

  private DataCreateMsg encodeCreate(BatchCreateItem item) {
    return new DataCreateMsg(item.getDataType(), item.getDataValue());

  }

  private DataUpdateMsg encodeUpdate(BatchUpdateItem item) {
    return new DataUpdateMsg(item.getDataType(), item.getDataId(), item.getIdField(),
        item.getPrimitiveUpdated(), item.getSetUpdated(), item.getMapUpdated());
  }

  private void clearBatch() {
    _batch.getCreateMap().clear();
    _batch.getUpdateMap().clear();
  }

  private final IoWaitBatch _batch;
}
