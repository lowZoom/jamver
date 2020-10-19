package luj.game.server.internal.data.save.wait.consume;

import java.util.List;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create.DataCreateMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;

final class ResultImpl implements DataIoWaitConsumer.Result {

  @Override
  public List<DataCreateMsg> created() {
    return _createList;
  }

  @Override
  public List<DataUpdateMsg> updated() {
    return _updateList;
  }

  List<DataCreateMsg> _createList;

  List<DataUpdateMsg> _updateList;
}
