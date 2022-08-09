package luj.game.server.internal.data.execute.load.request.node;

import java.util.List;
import java.util.Map;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;
import luj.game.server.internal.data.execute.load.request.node.find.finish.NodeIdOneFindFinish;
import luj.game.server.internal.data.execute.load.request.node.find.finish.lock.DataPair;
import luj.game.server.internal.data.execute.load.request.node.find.ready.NodeIdOneFindReady;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

final class NodeIdOneImpl implements LoadNodeOp {

  @Override
  public Object findWhenReady(RequestWalkListener.Context ctx,
      MissingLog missingOut, List<CacheItem> lockedOrLoadingOut) {
    return NodeIdOneFindReady.GET.find(
        _dataCache, ctx.getDataType(), _dataId, missingOut, lockedOrLoadingOut);
  }

  @Override
  public Data findWhenFinish(RequestWalkListener.Context ctx, Map<String, DataPair> loadLog,
      DataResultProxyV2.FieldHook fieldHook) {
    return NodeIdOneFindFinish.GET.find(_dataCache, ctx.getDataType(), _dataId, fieldHook, loadLog);
  }

  CacheContainer _dataCache;

  Comparable<?> _dataId;
}
