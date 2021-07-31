package luj.game.server.internal.data.load.cache;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.wake.CommandQueueWaker;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.DataEntityCreator;
import luj.game.server.internal.data.instancev2.DataType;
import luj.game.server.internal.data.save.create.DataCreateRequestorV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheLoadFinisher {

  public CacheLoadFinisher(Class<?> dataType, Comparable<?> dataId, boolean present,
      Map<String, Object> dataValue, CacheContainer dataCache,
      Queue<DataCommandRequest> commandQueue, Tellable dataRef, Tellable saveRef, Tellable loadRef,
      BeanContext lujbean) {
    _dataType = dataType;
    _dataId = dataId;
    _present = present;
    _dataValue = dataValue;
    _dataCache = dataCache;
    _commandQueue = commandQueue;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _loadRef = loadRef;
    _lujbean = lujbean;
  }

  public void finish() {
    updateCache();

    // 有新数据可用后，唤醒之前等待的CMD
    new CommandQueueWaker(_commandQueue, _dataCache, _dataRef, _saveRef, _loadRef, _lujbean).wake();
  }

  private void updateCache() {
    String dataKey = CacheKeyMaker.create(_dataType, _dataId).make();
    CacheItem cacheItem = _dataCache.get(dataKey);
    checkNotNull(cacheItem, dataKey);
    checkState(cacheItem.getPresence() == DataPresence.LOADING, dataKey);

    DataType typeV2 = cacheItem.getDataTypeV2();
    checkState(Objects.equals(typeV2.getName(), _dataType.getName()));

    LOG.debug("[game]缓存项读取完成：{}", dataKey);
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

    DataCreateRequestorV2.create(ImmutableList.of(dataObj), _saveRef).request();
  }

  private static final Logger LOG = LoggerFactory.getLogger(CacheLoadFinisher.class);

  private final Class<?> _dataType;
  private final Comparable<?> _dataId;

  private final boolean _present;
  private final Map<String, Object> _dataValue;

  private final CacheContainer _dataCache;
  private final Queue<DataCommandRequest> _commandQueue;

  private final Tellable _dataRef;
  private final Tellable _saveRef;
  private final Tellable _loadRef;

  private final BeanContext _lujbean;
}
