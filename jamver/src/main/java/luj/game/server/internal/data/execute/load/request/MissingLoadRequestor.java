package luj.game.server.internal.data.execute.load.request;

import java.util.List;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.execute.load.missing.LoadMissingCollector;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.load.DataLoadMsg;

public class MissingLoadRequestor {

  public MissingLoadRequestor(List<LoadMissingCollector.Missing> missList,
      ActorPreStartHandler.Actor loadRef) {
    _missList = missList;
    _loadRef = loadRef;
  }

  public void request() {
    for (LoadMissingCollector.Missing miss : _missList) {
      Class<?> dataType = miss.dataType();
      Comparable<?> dataId = miss.dataId();

      //FIXME: 需要一个单独的机制取数据id
      DataLoadMsg msg = new DataLoadMsg(dataType, dataId, "id");

      _loadRef.tell(msg);
    }
  }

  private final List<LoadMissingCollector.Missing> _missList;

  private final ActorPreStartHandler.Actor _loadRef;
}
