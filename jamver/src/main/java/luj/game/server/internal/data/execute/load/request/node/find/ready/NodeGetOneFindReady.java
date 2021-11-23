package luj.game.server.internal.data.execute.load.request.node.find.ready;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.DataType;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public enum NodeGetOneFindReady {
  GET;

  public Object find(RequestWalkListener.Context ctx, CacheContainer cache,
      Function<Object, Comparable<?>> idGetter, MissingLog missingOut,
      List<CacheItem> lockedOrLoadingOut) {
    CacheItem parentItem = ctx.getParentReturn();
    if (parentItem == null || parentItem.getPresence() != DataPresence.PRESENT) {
      return ImmutableList.of();
    }

    DataEntity parentData = parentItem.getDataObjV2();
    DataResultProxyV2 parentResult = getProxy(parentData);
    Comparable<?> dataId = idGetter.apply(parentResult.getInstance());

    Class<?> dataType = ctx.getDataType();
    NodeIdOneFindReady util = NodeIdOneFindReady.GET;

    return util.find(cache, dataType, dataId, missingOut, lockedOrLoadingOut);
  }

  //FIXME: 增加对象缓存
  DataResultProxyV2 getProxy(DataEntity entity) {
    DataType type = entity.getDataType();
    Class<?> dataClass = type.getClassCache();
    checkNotNull(dataClass, type.getName());
    return DataResultProxyV2.create(entity, dataClass, (p, s) -> {
    });
  }
}
