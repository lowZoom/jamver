package luj.game.server.internal.data.load.cache;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableList;
import java.util.Map;
import java.util.Queue;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.wake.CommandQueueWaker;
import luj.game.server.internal.data.instance.DataInstanceCreator;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.save.create.request.DataCreateRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheLoadFinisher {

  public CacheLoadFinisher(Class<?> dataType, Comparable<?> dataId, boolean present,
      Map<String, Object> dataValue, CacheContainer dataCache,
      Queue<DataCommandRequest> commandQueue, ActorMessageHandler.Ref dataRef,
      ActorPreStartHandler.Actor saveRef, ActorPreStartHandler.Actor loadRef,
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
    String dataKey = new CacheKeyMaker(_dataType, _dataId).make();

    CacheItem cacheItem = _dataCache.get(dataKey);
    checkNotNull(cacheItem, dataKey);
    checkState(cacheItem.getPresence() == DataPresence.LOADING, dataKey);

    LOG.debug("[game]缓存项读取完成：{}", dataKey);
    cacheItem.setPresence(_present ? DataPresence.PRESENT : DataPresence.ABSENT);

    if (_present) {
      DataTempProxy dataObj = new DataInstanceCreator(_dataType, _dataValue).create();
      cacheItem.setDataObj(dataObj);
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

    Class<?> dataType = item.getDataType();
    DataTempProxy dataObj = new DataInstanceCreator(dataType).create();
    dataObj.getDataMap().put(DataTempProxy.ID, DataTempProxy.GLOBAL);

    item.setPresence(DataPresence.PRESENT);
    item.setDataObj(dataObj);

    new DataCreateRequestor(ImmutableList.of(dataObj), _saveRef).request();
  }

  private static final Logger LOG = LoggerFactory.getLogger(CacheLoadFinisher.class);

  private final Class<?> _dataType;
  private final Comparable<?> _dataId;

  private final boolean _present;
  private final Map<String, Object> _dataValue;

  private final CacheContainer _dataCache;
  private final Queue<DataCommandRequest> _commandQueue;

  private final ActorMessageHandler.Ref _dataRef;
  private final ActorPreStartHandler.Actor _saveRef;
  private final ActorPreStartHandler.Actor _loadRef;

  private final BeanContext _lujbean;
}
