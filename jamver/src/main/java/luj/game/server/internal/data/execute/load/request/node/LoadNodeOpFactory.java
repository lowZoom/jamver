package luj.game.server.internal.data.execute.load.request.node;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;

public class LoadNodeOpFactory {

  public LoadNodeOpFactory(CacheContainer dataCache) {
    _dataCache = dataCache;
  }

  public LoadNodeOp createIdOne(Comparable<?> id) {
    NodeIdOneImpl op = new NodeIdOneImpl();
    op._dataId = checkNotNull(id);
    op._dataCache = _dataCache;
    return op;
  }

  @SuppressWarnings("unchecked")
  public LoadNodeOp createIdMulti(Collection<?> id) {
    NodeIdMultiImpl op = new NodeIdMultiImpl();
    op._idList = (Collection<Comparable<?>>) id;
    op._dataCache = _dataCache;
    return op;
  }

  @SuppressWarnings("unchecked")
  public LoadNodeOp createGetOne(Function<?, ?> idGetter) {
    NodeGetOneImpl op = new NodeGetOneImpl();
    op._idGetter = (Function<Object, Comparable<?>>) idGetter;
    op._dataCache = _dataCache;
    return op;
  }

  @SuppressWarnings("unchecked")
  public LoadNodeOp createGetMulti(Function<?, ?> idGetter) {
    NodeGetMultiImpl op = new NodeGetMultiImpl();
    op._idGetter = (Function<Object, Collection<Comparable<?>>>) idGetter;
    op._dataCache = _dataCache;
    return op;
  }

  private final CacheContainer _dataCache;
}
