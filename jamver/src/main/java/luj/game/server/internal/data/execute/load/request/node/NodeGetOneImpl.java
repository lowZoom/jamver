package luj.game.server.internal.data.execute.load.request.node;

import java.util.List;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;
import luj.game.server.internal.data.execute.load.request.node.find.finish.NodeGetOneFindFinish;
import luj.game.server.internal.data.execute.load.request.node.find.ready.NodeGetOneFindReady;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

final class NodeGetOneImpl implements LoadNodeOp {

  @Override
  public Object findWhenReady(RequestWalkListener.Context ctx,
      MissingLog missingOut, List<CacheItem> lockedOrLoadingOut) {
    return NodeGetOneFindReady.GET.find(ctx, _dataCache, _idGetter, missingOut, lockedOrLoadingOut);
  }

  @Override
  public Data findWhenFinish(RequestWalkListener.Context ctx, List<DataEntity> loadLog,
      DataResultProxyV2.FieldHook fieldHook) {
    return NodeGetOneFindFinish.GET.find(ctx, _dataCache, _idGetter, fieldHook, loadLog);
  }

  CacheContainer _dataCache;

  Function<Object, Comparable<?>> _idGetter;
}
