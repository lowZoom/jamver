package luj.game.server.internal.data.execute.save;

import static java.util.stream.Collectors.toMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.data.annotation.Transient;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create.DataCreateMsg;

public class DataSaveRequestor {

  public DataSaveRequestor(ActorPreStartHandler.Actor saveRef, List<DataTempProxy> createLog) {
    _saveRef = saveRef;
    _createLog = createLog;
  }

  public void request() {
    _createLog.stream()
        .filter(d -> !isTransient(d))
        .forEach(this::sendCreateMsg);
  }

  private boolean isTransient(DataTempProxy dataObj) {
    Class<?> dataType = dataObj.getDataType();
    return dataType.isAnnotationPresent(Transient.class);
  }

  private void sendCreateMsg(DataTempProxy dataObj) {
    Map<String, Serializable> dataValue = dataObj.getDataMap().entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> (Serializable) e.getValue()));

    DataCreateMsg msg = new DataCreateMsg(dataObj.getDataType(), dataValue);
    _saveRef.tell(msg);
  }

  private final ActorPreStartHandler.Actor _saveRef;

  private final List<DataTempProxy> _createLog;
}
