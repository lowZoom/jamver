package luj.game.server.internal.data.execute.load.request.node.find.finish;

import java.util.List;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public enum NodeGetOneFindFinish {
  GET;

  public LoadNodeOp.Data find(RequestWalkListener.Context ctx, CacheContainer dataCache,
      Function<Object, Comparable<?>> idGetter, DataResultProxyV2.FieldHook fieldHook,
      List<DataEntity> loadLog) {
    DataEntity parentData = ctx.getParentReturn();

    NodeIdOneFindFinish util = NodeIdOneFindFinish.GET;
    Class<?> dataType = ctx.getDataType();

    Object parentInstance = parentData.getResultCache().getInstance();
    Comparable<?> dataId = idGetter.apply(parentInstance);

    return util.find(dataCache, dataType, dataId, fieldHook, loadLog);
  }
}
