package luj.game.server.internal.data.execute.load.request.node.find.finish;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;
import luj.game.server.internal.data.execute.load.request.node.find.finish.lock.DataPair;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public enum NodeGetMultiFindFinish {
  GET;

  public LoadNodeOp.Data find(RequestWalkListener.Context ctx, CacheContainer dataCache,
      Function<Object, Collection<Comparable<?>>> idGetter, DataResultProxyV2.FieldHook fieldHook,
      Map<String, DataPair> loadLog) {
    NodeIdOneFindFinish util = NodeIdOneFindFinish.GET;
    DataResultProxyV2 parentResult = util.getParentResult(ctx);
    Object parentInstance = parentResult.getInstance();

    //FIXME: load跟exec会在不同线程执行，可能会ConcurrentModificationException
    Collection<Comparable<?>> idList = idGetter.apply(parentInstance);

    Class<?> dataType = ctx.getDataType();
    List<Object> dataList = ImmutableList.copyOf(idList.stream()
//        .peek(id -> LOG.debug("数组读取读取读取读取：{}, {}", dataType.getName(), id))
        .map(id -> util.getDataObjAndLock(dataCache, dataType, id, loadLog))
        .filter(Objects::nonNull)
        .map(d -> util.createResultAndLog(d, dataType, fieldHook, loadLog))
        .map(DataResultProxyV2::getInstance)
        .iterator());

    return new LoadNodeOp.Data() {
      @Override
      public Object getResultToSet() {
        return dataList;
      }

      @Override
      public Object getReturnAsNextParent() {
        return dataList;
      }
    };
  }
}
