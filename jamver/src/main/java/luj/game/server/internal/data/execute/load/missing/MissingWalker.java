package luj.game.server.internal.data.execute.load.missing;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;

final class MissingWalker implements RequestWalkListener {

  MissingWalker(CacheContainer cache, List<LoadMissingCollector.Missing> out) {
    _cache = cache;
    _out = out;
  }

  /**
   * @see luj.game.server.internal.data.execute.finish.FinishWalker#loadData
   */
  @Override
  public Object onWalk(Context ctx) {
    Class<?> dataType = ctx.getDataType();
    Comparable<?> dataId = ctx.getDataId();

    if (dataId == null) {
      return walkList(ctx.getParentReturn(), ctx.getDataIdGetter(), dataType);
    }

    CacheItem dataItem = cacheGet(dataType, dataId);
    if (dataItem == null) {
      _out.add(new MissingImpl(dataType, dataId));
      return null;
    }

    return dataItem;
  }

  private List<CacheItem> walkList(CacheItem parentItem,
      Function<Object, Collection<Comparable<?>>> idGetter, Class<?> dataType) {
    if (parentItem == null || !parentItem.isPresent()) {
      return ImmutableList.of();
    }

    Object dataInstance = parentItem.getDataObj().getInstance();
    Collection<Comparable<?>> idList = idGetter.apply(dataInstance);

    for (Comparable<?> id : idList) {
      if (cacheGet(dataType, id) != null) {
        continue;
      }
      _out.add(new MissingImpl(dataType, id));
    }

    return ImmutableList.of();
  }

  private CacheItem cacheGet(Class<?> dataType, Object dataId) {
    String dataKey = new CacheKeyMaker(dataType, dataId).make();
    return _cache.get(dataKey);
  }

  private final CacheContainer _cache;

  private final List<LoadMissingCollector.Missing> _out;
}
