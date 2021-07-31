package luj.game.server.internal.data.execute.load.request.node.find.ready;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.DataType;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public enum NodeGetMultiFindReady {
  GET;

  public Object find(RequestWalkListener.Context ctx, CacheContainer cache,
      Function<Object, Collection<Comparable<?>>> idGetter,
      List<DataReadyChecker.Missing> missingOut, List<CacheItem> lockedOrLoadingOut) {
    CacheItem parentItem = ctx.getParentReturn();
    if (parentItem == null || parentItem.getPresence() != DataPresence.PRESENT) {
      return ImmutableList.of();
    }

    DataEntity parentData = parentItem.getDataObjV2();
    DataResultProxyV2 parentResult = getProxy(parentData);
    Collection<Comparable<?>> idList = idGetter.apply(parentResult.getInstance());

    Class<?> dataType = ctx.getDataType();
    NodeIdOneFindReady util = NodeIdOneFindReady.GET;
    for (Comparable<?> id : idList) {
      CacheItem elemItem = util.cacheGet(cache, dataType, id);
      util.logUnusable(elemItem, dataType, id, missingOut, lockedOrLoadingOut);
    }

    return ImmutableList.of();
  }

  //FIXME: 增加对象缓存
  private DataResultProxyV2 getProxy(DataEntity entity) {
    DataType type = entity.getDataType();
    Class<?> dataClass = type.getClassCache();
    checkNotNull(dataClass, type.getName());
    return DataResultProxyV2.create(entity, dataClass, (p, s) -> {
    });
  }
}
