package luj.game.server.internal.data.load.cache;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableList;
import java.util.Map;
import java.util.Objects;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.command.queue.wake.CommandQueueWaker;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.DataEntityCreator;
import luj.game.server.internal.data.instancev2.DataType;
import luj.game.server.internal.data.save.create.DataCreateRequestorV2;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class CacheLoadFinisher {

  public static CacheLoadFinisher get(GameplayDataActor dataState, Tellable dataRef,
      Class<?> dataType, Comparable<?> dataId, boolean present, Map<String, Object> dataValue) {
    return new CacheLoadFinisher(dataType, dataId, present, dataValue, dataState,
        dataState.getIdGenState(), dataRef, dataState.getSaveRef());
  }

  public CacheLoadFinisher(Class<?> dataType, Comparable<?> dataId, boolean present,
      Map<String, Object> dataValue, GameplayDataActor dataState, DataIdGenState idState,
      Tellable dataRef, Tellable saveRef) {
    _dataType = dataType;
    _dataId = dataId;
    _present = present;
    _dataValue = dataValue;
    _dataState = dataState;
    _idState = idState;
    _dataRef = dataRef;
    _saveRef = saveRef;
  }

  public void finish() {
    CacheContainer cache = _dataState.getDataCache();
    updateCache(cache);

    Tellable loadRef = _dataState.getLoadRef();
    Tellable eventRef = _dataState.getSiblingRef().getEventRef();

    // 有新数据可用后，唤醒之前等待的CMD
    new CommandQueueWaker(_dataState.getCommandQueue(), cache, _idState, _dataState.getConfigs(),
        _dataRef, _saveRef, loadRef, eventRef, _dataState.getLujbean()).wake();
  }

  private void updateCache(CacheContainer cache) {
    String dataKey = CacheKeyMaker.create(_dataType, _dataId).make();
    CacheItem cacheItem = cache.get(dataKey);
    checkNotNull(cacheItem, dataKey);
    checkState(cacheItem.getPresence() == DataPresence.LOADING, dataKey);

    DataType typeV2 = cacheItem.getDataTypeV2();
    checkState(Objects.equals(typeV2.getName(), _dataType.getName()));

//    LOG.debug("[game]缓存项读取完成：{}", dataKey);
    cacheItem.setPresence(_present ? DataPresence.PRESENT : DataPresence.ABSENT);

    if (_present) {
      Map<String, Object> valueMap = new DataValueDecoder(_dataValue).decode();
      DataEntity dataObj = new DataEntityCreator(typeV2, _dataId, valueMap).create();
      cacheItem.setDataObjV2(dataObj);
    }

    handleGlobalAbsent(cacheItem, dataKey);
  }

  private void handleGlobalAbsent(CacheItem item, String dataKey) {
    DataPresence dataPresence = item.getPresence();
    if (dataPresence == DataPresence.PRESENT || !_dataId.equals(DataTempProxy.GLOBAL)) {
      return;
    }

    checkState(dataPresence == DataPresence.ABSENT, dataKey);
    checkState(_dataId.equals(DataTempProxy.GLOBAL));

    DataType dataType = item.getDataTypeV2();
    DataEntity dataObj = DataEntityCreator.create(dataType, DataTempProxy.GLOBAL).create();
//    dataObj.getDataMap().put(DataTempProxy.ID, DataTempProxy.GLOBAL);

    item.setPresence(DataPresence.PRESENT);
    item.setDataObjV2(dataObj);

    new DataCreateRequestorV2(ImmutableList.of(dataObj), _idState.getIdField(), _saveRef).request();
  }

//  private static final Logger LOG = LoggerFactory.getLogger(CacheLoadFinisher.class);

  private final Class<?> _dataType;
  private final Comparable<?> _dataId;

  private final boolean _present;
  private final Map<String, Object> _dataValue;

  private final GameplayDataActor _dataState;
  private final DataIdGenState _idState;

  private final Tellable _dataRef;
  private final Tellable _saveRef;
}
