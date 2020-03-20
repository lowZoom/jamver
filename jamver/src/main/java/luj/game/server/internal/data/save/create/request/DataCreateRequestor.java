package luj.game.server.internal.data.save.create.request;

import java.util.List;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.save.DataTransientChecker;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create.DataCreateMsg;

public class DataCreateRequestor {

  public DataCreateRequestor(List<DataTempProxy> createList, ActorPreStartHandler.Actor saveRef) {
    _createList = createList;
    _saveRef = saveRef;
  }

  public void request() {
    _createList.stream()
        .filter(d -> !isTransient(d))
        .forEach(this::sendCreateMsg);
  }

  private boolean isTransient(DataTempProxy dataObj) {
    return new DataTransientChecker(dataObj).check();
  }

  private void sendCreateMsg(DataTempProxy dataObj) {
    DataCreateMsg msg = new DataCreateMsg(dataObj.getDataType(), dataObj.getDataMap());
    _saveRef.tell(msg);
  }

  private final List<DataTempProxy> _createList;

  private final ActorPreStartHandler.Actor _saveRef;
}
