package luj.game.server.internal.data.execute.finish;

import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.execute.save.CommandSaveRequestor;
import luj.game.server.internal.data.instance.DataTempAdder;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultProxy;

public class ExecDataFinisher {

  public ExecDataFinisher(CacheContainer dataCache, Tellable saveRef, List<DataTempProxy> createLog,
      List<DataResultProxy> loadLog) {
    _dataCache = dataCache;
    _saveRef = saveRef;
    _createLog = createLog;
    _loadLog = loadLog;
  }

  public void finish() {
    for (DataTempProxy data : _createLog) {
      new DataTempAdder(_dataCache, data.getDataType(), data).add();
    }

    new CommandSaveRequestor(_saveRef, _createLog, _loadLog).request();

    //TODO: 将数据变更应用到数据上
  }

  private final CacheContainer _dataCache;
  private final Tellable _saveRef;

  private final List<DataTempProxy> _createLog;
  private final List<DataResultProxy> _loadLog;
}
