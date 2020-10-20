package luj.game.server.internal.data.save.wait.consume;

import java.util.List;
import luj.game.server.internal.data.save.wait.BatchCreateItem;
import luj.game.server.internal.data.save.wait.BatchUpdateItem;

final class ResultImpl implements DataIoWaitConsumer.Result {

  @Override
  public List<BatchCreateItem> created() {
    return _createList;
  }

  @Override
  public List<BatchUpdateItem> updated() {
    return _updateList;
  }

  List<BatchCreateItem> _createList;

  List<BatchUpdateItem> _updateList;
}
