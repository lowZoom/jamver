package luj.game.server.internal.data.execute.load.request.node;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;

public class LoadNodeOpFactory {

  public LoadNodeOpFactory(CacheContainer dataCache) {
    _dataCache = dataCache;
  }

  public LoadNodeOp createIdOne(Long id) {
    NodeIdOneImpl op = new NodeIdOneImpl();
    op._dataId = checkNotNull(id);
    op._dataCache = _dataCache;
    return op;
  }

  public LoadNodeOp createGetMulti(Function<?, ?> idGetter) {
    NodeGetMultiImpl op = new NodeGetMultiImpl();
    op._idGetter = (Function<Object, Collection<Comparable<?>>>) idGetter;
    op._dataCache = _dataCache;
    return op;
  }

  private final CacheContainer _dataCache;
}
