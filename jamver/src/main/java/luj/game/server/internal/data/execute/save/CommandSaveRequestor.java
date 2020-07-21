package luj.game.server.internal.data.execute.save;

import java.util.List;
import java.util.Map;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultProxy;
import luj.game.server.internal.data.save.DataTransientChecker;
import luj.game.server.internal.data.save.create.request.DataCreateRequestor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;

public class CommandSaveRequestor {

  public CommandSaveRequestor(Tellable saveRef, List<DataTempProxy> createLog,
      List<DataResultProxy> loadLog) {
    _saveRef = saveRef;
    _createLog = createLog;
    _loadLog = loadLog;
  }

  public void request() {
    new DataCreateRequestor(_createLog, _saveRef).request();

    _loadLog.stream()
        .filter(DataResultProxy::isDirty)
        .map(DataResultProxy::getData)
        .filter(d -> !isTransient(d))
        .forEach(this::sendUpdateMsg);
  }

  private boolean isTransient(DataTempProxy dataObj) {
    return new DataTransientChecker(dataObj).check();
  }

  private void sendUpdateMsg(DataTempProxy dataObj) {
    Map<String, Object> dataMap = dataObj.getDataMap();
    Comparable<?> dataId = (Comparable<?>) dataMap.get(DataTempProxy.ID);

    DataUpdateMsg msg = new DataUpdateMsg(dataObj.getDataType(), dataId, DataTempProxy.ID, dataMap);
    _saveRef.tell(msg);
  }

  private final Tellable _saveRef;

  private final List<DataTempProxy> _createLog;
  private final List<DataResultProxy> _loadLog;
}
