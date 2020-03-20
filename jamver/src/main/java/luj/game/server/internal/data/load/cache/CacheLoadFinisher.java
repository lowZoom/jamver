package luj.game.server.internal.data.load.cache;

import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableList;
import java.util.Map;
import java.util.Queue;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
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
      ActorPreStartHandler.Actor saveRef, ActorPreStartHandler.Actor loadRef) {
    _dataType = dataType;
    _dataId = dataId;
    _present = present;
    _dataValue = dataValue;
    _dataCache = dataCache;
    _commandQueue = commandQueue;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _loadRef = loadRef;
  }

  public void finish() {
    updateCache();

    // 有新数据可用后，唤醒之前等待的CMD
    new CommandQueueWaker(_commandQueue, _dataCache, _dataRef, _saveRef, _loadRef).wake();
  }

  private void updateCache() {
    String dataKey = new CacheKeyMaker(_dataType, _dataId).make();
    checkState(_dataCache.get(dataKey) == null, dataKey);

    CacheItem item = new CacheItem(_dataType);
    item.setPresent(_present);

    if (_present) {
      DataTempProxy dataObj = new DataInstanceCreator(_dataType, _dataValue).create();
      item.setDataObj(dataObj);
    }

    LOG.debug("[game]缓存新增项：{}", dataKey);
    _dataCache.put(dataKey, item);

    handleGlobalAbsent(item);
  }

  private void handleGlobalAbsent(CacheItem item) {
    if (item.isPresent() || !_dataId.equals(DataTempProxy.GLOBAL)) {
      return;
    }

    Class<?> dataType = item.getDataType();
    DataTempProxy dataObj = new DataInstanceCreator(dataType).create();
    dataObj.getDataMap().put(DataTempProxy.ID, DataTempProxy.GLOBAL);

    item.setPresent(true);
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
}
