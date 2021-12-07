package luj.game.server.internal.data.execute.finish;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.stream.Collectors;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.execute.save.CommandSaveRequestorV2;
import luj.game.server.internal.data.instance.value.change.DataDirtyChecker;
import luj.game.server.internal.data.instance.value.change.DataModificationApplier;
import luj.game.server.internal.data.instancev2.CacheEntityAdder;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.lock.CacheItemUnlocker;
import luj.game.server.internal.data.types.map.history.MapDataGetter;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public class ExecDataFinisherV2 {

  public ExecDataFinisherV2(CacheContainer dataCache, Tellable saveRef, String idField,
      List<DataEntity> createLog, List<DataEntity> loadLog) {
    _dataCache = dataCache;
    _saveRef = saveRef;
    _idField = idField;
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
      guessIdIfNull(data);
    }

    // 数据变更部分发起写库
    new CommandSaveRequestorV2(_saveRef, _idField, _createLog, modifyLog).request();

    // 将已有数据的变更应用到数据上
    for (DataEntity data : modifyLog) {
      DataModificationApplier.createV2(data).apply();
    }

    // 将新创建的数据加入统一缓存管理
    for (DataEntity data : _createLog) {
      new CacheEntityAdder(_dataCache, data).add();
    }

    // 归还（解锁）借出的数据
    for (DataEntity data : _loadLog) {
      CacheItemUnlocker.GET.unlock(_dataCache, data);
    }
  }

  private void guessIdIfNull(DataEntity data) {
    if (data.getDataId() != null) {
      return;
    }

    MapWithHistory<String, Object> value = data.getFieldValueMap();
    Object newId = MapDataGetter.GET.getValue(value, _idField);

    checkNotNull(newId, data.getDataType().getName());
    data.setDataId((Comparable<?>) newId);
  }

  private final CacheContainer _dataCache;

  private final Tellable _saveRef;
  private final String _idField;

  private final List<DataEntity> _createLog;
  private final List<DataEntity> _loadLog;
}
