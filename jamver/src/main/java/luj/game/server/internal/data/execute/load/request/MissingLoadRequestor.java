package luj.game.server.internal.data.execute.load.request;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.load.DataLoadMsg;

public class MissingLoadRequestor {

  public static MissingLoadRequestor create(List<DataReadyChecker.Missing> missList,
      CacheContainer dataCache, Tellable loadRef) {
    return new MissingLoadRequestor(missList, DataTempProxy.ID, dataCache, loadRef);
  }

  public MissingLoadRequestor(List<DataReadyChecker.Missing> missList,
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
      createLoadingItem(dataType, dataId);

      //FIXME: 需要一个单独的机制取数据id
      DataLoadMsg msg = new DataLoadMsg(dataType, dataId, _idField);

      _loadRef.tell(msg);
    }
  }

  private void createLoadingItem(Class<?> dataType, Comparable<?> dataId) {
    String key = CacheKeyMaker.create(dataType, dataId).make();
    checkState(_dataCache.get(key) == null, key);

    CacheItem cacheItem = CacheItem.create(dataType);
    cacheItem.setPresence(DataPresence.LOADING);

    _dataCache.put(key, cacheItem);
  }

  private final List<DataReadyChecker.Missing> _missList;
  private final String _idField;

  private final CacheContainer _dataCache;
  private final Tellable _loadRef;
}
