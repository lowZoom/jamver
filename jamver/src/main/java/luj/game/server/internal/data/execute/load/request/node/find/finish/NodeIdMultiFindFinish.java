package luj.game.server.internal.data.execute.load.request.node.find.finish;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;
import luj.game.server.internal.data.execute.load.request.node.find.finish.lock.DataPair;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public enum NodeIdMultiFindFinish {
  GET;

  public LoadNodeOp.Data find(CacheContainer dataCache, Class<?> dataType,
      Collection<Comparable<?>> idList, DataResultProxyV2.FieldHook fieldHook,
      Map<String, DataPair> loadLog) {
    NodeIdOneFindFinish util = NodeIdOneFindFinish.GET;

    List<Object> result = idList.stream()
        .map(id -> util.getDataObjAndLock(dataCache, dataType, id, loadLog))
        .filter(Objects::nonNull)
        .map(e -> util.createResultAndLog(e, dataType, fieldHook, loadLog))
        .map(DataResultProxyV2::getInstance)
        .collect(Collectors.toList());

    //TODO: 多ID续借后面再实现
    return new DataImpl(result, ImmutableList.of());
  }
}
