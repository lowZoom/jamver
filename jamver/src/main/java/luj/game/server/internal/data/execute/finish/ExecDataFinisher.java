package luj.game.server.internal.data.execute.finish;

import java.util.List;
import java.util.stream.Collectors;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.execute.save.CommandSaveRequestor;
import luj.game.server.internal.data.instance.DataTempAdder;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.instance.value.change.DataDirtyChecker;
import luj.game.server.internal.data.instance.value.change.DataModificationApplier;

public class ExecDataFinisher {

  public ExecDataFinisher(CacheContainer dataCache, Tellable saveRef, List<DataTempProxy> createLog,
      List<DataTempProxy> loadLog) {
    _dataCache = dataCache;
    _saveRef = saveRef;
    _createLog = createLog;
    _loadLog = loadLog;
  }

  public void finish() {
    List<DataTempProxy> modifyLog = _loadLog.stream()
        .filter(DataDirtyChecker.GET::isDirty)
        .collect(Collectors.toList());

    // 新创的数据要在写库前应用变更
    for (DataTempProxy data : _createLog) {
      DataModificationApplier.create(data).apply();
    }

    // 变动的数据发起写库
    new CommandSaveRequestor(_saveRef, _createLog, modifyLog).request();

    // 将已有数据的变更应用到数据上
    for (DataTempProxy data : modifyLog) {
      DataModificationApplier.create(data).apply();
    }

    // 将新创建的数据加入统一缓存管理
    for (DataTempProxy data : _createLog) {
      new DataTempAdder(_dataCache, data.getDataType(), data).add();
    }
  }

  private final CacheContainer _dataCache;
  private final Tellable _saveRef;

  private final List<DataTempProxy> _createLog;
  private final List<DataTempProxy> _loadLog;
}
