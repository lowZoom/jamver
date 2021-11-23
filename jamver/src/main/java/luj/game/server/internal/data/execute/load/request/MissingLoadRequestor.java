package luj.game.server.internal.data.execute.load.request;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.instancev2.DataType;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.load.DataLoadMsg;

public class MissingLoadRequestor {

  public MissingLoadRequestor(Collection<DataReadyChecker.Missing> missList,
      String idField, CacheContainer dataCache, Tellable loadRef) {
    _missList = missList;
    _idField = idField;
    _dataCache = dataCache;
    _loadRef = loadRef;
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.load.OnDataLoad#onHandle
   */
  public void request() {
    for (DataReadyChecker.Missing miss : _missList) {
      Class<?> dataType = miss.dataType();
      Comparable<?> dataId = miss.dataId();

      checkNotNull(dataId, dataType.getName());
      createLoadingItem(dataType, dataId);

      DataLoadMsg msg = new DataLoadMsg(dataType, dataId, _idField);
      _loadRef.tell(msg);
    }
  }

  private void createLoadingItem(Class<?> dataClass, Comparable<?> dataId) {
    String key = CacheKeyMaker.create(dataClass, dataId).make();
    checkState(_dataCache.get(key) == null, key);

    //TODO: 从一个总缓存里拿
    DataType dataType = DataType.create(dataClass);

    CacheItem cacheItem = CacheItem.create(dataType);
    cacheItem.setPresence(DataPresence.LOADING);

    _dataCache.put(key, cacheItem);
  }

  private final Collection<DataReadyChecker.Missing> _missList;
  private final String _idField;

  private final CacheContainer _dataCache;
  private final Tellable _loadRef;
}
