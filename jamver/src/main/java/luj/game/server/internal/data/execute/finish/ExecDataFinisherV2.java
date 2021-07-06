package luj.game.server.internal.data.execute.finish;

import java.util.List;
import java.util.stream.Collectors;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.execute.save.CommandSaveRequestorV2;
import luj.game.server.internal.data.instance.value.change.DataDirtyChecker;
import luj.game.server.internal.data.instance.value.change.DataModificationApplier;
import luj.game.server.internal.data.instancev2.CacheEntityAdder;
import luj.game.server.internal.data.instancev2.DataEntity;

public class ExecDataFinisherV2 {

  public ExecDataFinisherV2(CacheContainer dataCache, Tellable saveRef, List<DataEntity> createLog,
      List<DataEntity> loadLog) {
    _dataCache = dataCache;
    _saveRef = saveRef;
    _createLog = createLog;
    _loadLog = loadLog;
  }

  public void finish() {
    List<DataEntity> modifyLog = _loadLog.stream()
        .filter(DataDirtyChecker.GET::isDirty)
        .collect(Collectors.toList());

    // 新创的数据要在写库前应用变更
    for (DataEntity data : _createLog) {
      DataModificationApplier.createV2(data).apply();
    }

    // 变动的数据发起写库
    new CommandSaveRequestorV2(_saveRef, _createLog, modifyLog).request();

    // 将已有数据的变更应用到数据上
    for (DataEntity data : modifyLog) {
      DataModificationApplier.createV2(data).apply();
    }

    // 将新创建的数据加入统一缓存管理
    for (DataEntity data : _createLog) {
      new CacheEntityAdder(_dataCache, data).add();
    }
  }

  private final CacheContainer _dataCache;
  private final Tellable _saveRef;

  private final List<DataEntity> _createLog;
  private final List<DataEntity> _loadLog;
}
