package luj.game.server.internal.data.execute.load.missing;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;

final class ReadyWalker implements RequestWalkListener {

  /**
   * @see luj.game.server.internal.data.execute.finish.ExecFinishWalker#loadData
   */
  @Override
  public Object onWalk(Context ctx) {
    Class<?> dataType = ctx.getDataType();
    Comparable<?> dataId = ctx.getDataId();

    if (dataId == null) {
      return walkList(ctx.getParentReturn(), ctx.getDataIdGetter(), dataType);
    }

    CacheItem dataItem = cacheGet(dataType, dataId);
    logUnusable(dataItem, dataType, dataId);

    return dataItem;
  }

  private List<CacheItem> walkList(CacheItem parentItem,
      Function<Object, Object> idGetter, Class<?> dataType) {
    if (parentItem == null || parentItem.getPresence() != DataPresence.PRESENT) {
      return ImmutableList.of();
    }

    Object dataInstance = parentItem.getDataObj().getInstance();
    Collection<Comparable<?>> idList = (Collection<Comparable<?>>) idGetter.apply(dataInstance);

    for (Comparable<?> id : idList) {
      CacheItem elemItem = cacheGet(dataType, id);
      logUnusable(elemItem, dataType, id);
    }

    return ImmutableList.of();
  }

  private void logUnusable(CacheItem item, Class<?> dataType, Comparable<?> dataId) {
    if (item == null) {
      _missingOut.add(new MissingImpl(dataType, dataId));
      return;
    }

    if (item.getPresence() == DataPresence.LOADING) {
      _lockedOrLoadingOut.add(item);
    }
  }

  private CacheItem cacheGet(Class<?> dataType, Object dataId) {
    String dataKey = CacheKeyMaker.create(dataType, dataId).make();
    return _cache.get(dataKey);
  }

  CacheContainer _cache;

  List<CacheItem> _lockedOrLoadingOut;
  List<DataReadyChecker.Missing> _missingOut;
}
