package luj.game.server.internal.data.save.wait.consume;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.internal.data.save.wait.BatchCreateItem;
import luj.game.server.internal.data.save.wait.BatchUpdateItem;
import luj.game.server.internal.data.save.wait.IoWaitBatch;

public class DataIoWaitConsumer {

  public interface Result {

    List<BatchCreateItem> created();

    List<BatchUpdateItem> updated();
  }

  public DataIoWaitConsumer(IoWaitBatch batch) {
    _batch = batch;
  }

  public Result consume() {
    ResultImpl result = new ResultImpl();

    // 清空前复制保留
    result._createList = ImmutableList.copyOf(_batch.getCreateList());
    result._updateList = ImmutableList.copyOf(_batch.getUpdateMap().values());

    clearBatch();
    return result;
  }

  private void clearBatch() {
    _batch.getCreateList().clear();
    _batch.getUpdateMap().clear();
  }

  private final IoWaitBatch _batch;
}
