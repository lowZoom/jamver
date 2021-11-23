package luj.game.server.internal.data.execute.load.request.node.find.ready;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;

public enum NodeIdMultiFindReady {
  GET;

  public Object find(CacheContainer cache, Class<?> dataType, Collection<Comparable<?>> idList,
      MissingLog missingOut, List<CacheItem> lockedOrLoadingOut) {
    NodeIdOneFindReady util = NodeIdOneFindReady.GET;

    for (Comparable<?> id : idList) {
      CacheItem elemItem = util.cacheGet(cache, dataType, id);
      util.logUnusable(elemItem, dataType, id, missingOut, lockedOrLoadingOut);
    }

    return ImmutableList.of();
  }
}
