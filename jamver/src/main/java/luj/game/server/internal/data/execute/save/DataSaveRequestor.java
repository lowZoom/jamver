package luj.game.server.internal.data.execute.save;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create.DataCreateMsg;

public class DataSaveRequestor {

  public DataSaveRequestor(ActorPreStartHandler.Actor saveRef, List<DataTempProxy> createLog) {
    _saveRef = saveRef;
    _createLog = createLog;
  }

  public void request() {
    requestCreate();
  }

  private void requestCreate() {
    for (DataTempProxy dataObj : _createLog) {
      Map<String, Serializable> dataValue = dataObj.getDataMap().entrySet().stream()
          .collect(Collectors.toMap(Map.Entry::getKey, e -> (Serializable) e.getValue()));

      DataCreateMsg msg = new DataCreateMsg(dataObj.getDataType(), dataValue);
      _saveRef.tell(msg);
    }
  }

  private final ActorPreStartHandler.Actor _saveRef;

  private final List<DataTempProxy> _createLog;
}
