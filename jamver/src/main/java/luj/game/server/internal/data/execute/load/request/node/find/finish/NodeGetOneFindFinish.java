package luj.game.server.internal.data.execute.load.request.node.find.finish;

import java.util.Map;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;
import luj.game.server.internal.data.execute.load.request.node.find.finish.lock.DataPair;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public enum NodeGetOneFindFinish {
  GET;

  public LoadNodeOp.Data find(RequestWalkListener.Context ctx, CacheContainer dataCache,
      Function<Object, Comparable<?>> idGetter, DataResultProxyV2.FieldHook fieldHook,
      Map<String, DataPair> loadLog) {
    Class<?> dataType = ctx.getDataType();
    NodeIdOneFindFinish util = NodeIdOneFindFinish.GET;

    DataResultProxyV2 parentResult = util.getParentResult(ctx);
    Object parentInstance = parentResult.getInstance();
    Comparable<?> dataId = idGetter.apply(parentInstance);

    if (dataId == null) {
      return DataImpl.NULL;
    }
    return util.find(dataCache, dataType, dataId, fieldHook, loadLog);
  }
}
