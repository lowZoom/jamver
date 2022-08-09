package luj.game.server.internal.data.execute.load.request.node;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;
import luj.game.server.internal.data.execute.load.request.node.find.finish.NodeGetMultiFindFinish;
import luj.game.server.internal.data.execute.load.request.node.find.finish.lock.DataPair;
import luj.game.server.internal.data.execute.load.request.node.find.ready.NodeGetMultiFindReady;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

final class NodeGetMultiImpl implements LoadNodeOp {

  @Override
  public Object findWhenReady(RequestWalkListener.Context ctx,
      MissingLog missingOut, List<CacheItem> lockedOrLoadingOut) {
    return NodeGetMultiFindReady.GET.find(
        ctx, _dataCache, _idGetter, missingOut, lockedOrLoadingOut);
  }

  @Override
  public Data findWhenFinish(RequestWalkListener.Context ctx, Map<String, DataPair> loadLog,
      DataResultProxyV2.FieldHook fieldHook) {
    return NodeGetMultiFindFinish.GET.find(ctx, _dataCache, _idGetter, fieldHook, loadLog);
  }

  CacheContainer _dataCache;

  Function<Object, Collection<Comparable<?>>> _idGetter;
}
