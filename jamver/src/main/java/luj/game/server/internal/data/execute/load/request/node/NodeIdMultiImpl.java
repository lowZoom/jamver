package luj.game.server.internal.data.execute.load.request.node;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;
import luj.game.server.internal.data.execute.load.request.node.find.finish.NodeIdMultiFindFinish;
import luj.game.server.internal.data.execute.load.request.node.find.finish.lock.DataPair;
import luj.game.server.internal.data.execute.load.request.node.find.ready.NodeIdMultiFindReady;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

final class NodeIdMultiImpl implements LoadNodeOp {

  @Override
  public Object findWhenReady(RequestWalkListener.Context ctx,
      MissingLog missingOut, List<CacheItem> lockedOrLoadingOut) {
    Class<?> dataType = ctx.getDataType();
    return NodeIdMultiFindReady.GET.find(
        _dataCache, dataType, _idList, missingOut, lockedOrLoadingOut);
  }

  @Override
  public Data findWhenFinish(RequestWalkListener.Context ctx, Map<String, DataPair> loadLog,
      DataResultProxyV2.FieldHook fieldHook) {
    Class<?> dataType = ctx.getDataType();
    return NodeIdMultiFindFinish.GET.find(_dataCache, dataType, _idList, fieldHook, loadLog);
  }

  CacheContainer _dataCache;

  Collection<Comparable<?>> _idList;
}
