package luj.game.server.internal.data.execute.load.request.node;

import java.util.List;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public interface LoadNodeOp {

  interface Data {

    Object getResultToSet();

    Object getReturnAsNextParent();
  }

  Object findWhenReady(RequestWalkListener.Context ctx,
      MissingLog missingOut, List<CacheItem> lockedOrLoadingOut);

  Data findWhenFinish(RequestWalkListener.Context ctx,
      List<DataEntity> loadLog, DataResultProxyV2.FieldHook fieldHook);
}
