package luj.game.server.internal.data.execute.load.request.node.find.ready;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public enum NodeGetMultiFindReady {
  GET;

  public Object find(RequestWalkListener.Context ctx, CacheContainer cache,
      Function<Object, Collection<Comparable<?>>> idGetter, MissingLog missingOut,
      List<CacheItem> lockedOrLoadingOut) {
    CacheItem parentItem = ctx.getParentReturn();
    if (NodeGetOneFindReady.GET.cannotFollow(parentItem)) {
      return ImmutableList.of();
    }

    DataEntity parentData = parentItem.getDataObjV2();
    DataResultProxyV2 parentResult = getProxy(parentData);

    //FIXME: load跟exec会在不同线程执行，可能会ConcurrentModificationException
    Collection<Comparable<?>> idList = idGetter.apply(parentResult.getInstance());

    NodeIdOneFindReady util = NodeIdOneFindReady.GET;
    Class<?> dataType = ctx.getDataType();
    for (Comparable<?> id : idList) {
      CacheItem elemItem = util.cacheGet(cache, dataType, id);
      util.logIfUnusable(elemItem, dataType, id, missingOut, lockedOrLoadingOut);
    }

    return ImmutableList.of();
  }

  private DataResultProxyV2 getProxy(DataEntity entity) {
    return NodeGetOneFindReady.GET.getProxy(entity);
  }
}
