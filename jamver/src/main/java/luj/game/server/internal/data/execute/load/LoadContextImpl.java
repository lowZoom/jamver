package luj.game.server.internal.data.execute.load;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.data.find.FindCondition;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOpFactory;
import luj.game.server.internal.data.instance.DataTempProxy;

final class LoadContextImpl implements GameDataLoad.Context {

  LoadContextImpl(Object param, CacheContainer dataCache,
      CacheRequest cacheReq, ResultFieldProxy fieldHolder) {
    _param = param;
    _dataCache = dataCache;
    _cacheReq = cacheReq;
    _fieldHolder = fieldHolder;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> P param(GameDataLoad<P, ?> load) {
    return (P) _param;
  }

  @Override
  public <R, F> GameDataLoad.AndLoad<R, F> load(Function<R, F> field, Comparable<?> id) {
    return load(id, field);
  }

  @Override
  public <R, F> GameDataLoad.AndLoad<R, F> load(Comparable<?> id, Function<R, F> field) {
    LoadNodeOp op = opFactory().createIdOne(id);
    CacheRequest.Node child = ReqRootFieldAppender.GET
        .appendV2(_cacheReq, (Function<Object, ?>) field, _fieldHolder, op);
    return andLoad(child);
  }

  @Override
  public <R, F> GameDataLoad.AndLoad<R, F> load(GameDataLoad<?, R> load, Class<F> dataType,
      Comparable<?> id) {
    CacheRequest.Node child = ReqRootTransientAppender.GET.append(_cacheReq, dataType, id);
    return andLoad(child);
  }

  @Override
  public <R, F> GameDataLoad.AndLoad<R, F> loadGlobal(Function<R, F> field) {
    LoadNodeOp op = opFactory().createIdOne(DataTempProxy.GLOBAL);
    CacheRequest.Node child = ReqRootFieldAppender.GET
        .appendV2(_cacheReq, (Function<Object, ?>) field, _fieldHolder, op);
    return andLoad(child);
  }

  @Override
  public <R, F> GameDataLoad.AndLoad<R, F> loadGlobal(GameDataLoad<?, R> load, Class<F> dataType) {
    CacheRequest.Node child = ReqRootTransientAppender.GET
        .append(_cacheReq, dataType, DataTempProxy.GLOBAL);

    return andLoad(child);
  }

  @Override
  public <D, R> void find(Class<D> dataType, Consumer<FindCondition<D>> condition,
      Function<R, Collection<?>> resultField) {
    throw new UnsupportedOperationException("find");
  }

  private LoadNodeOpFactory opFactory() {
    return new LoadNodeOpFactory(_dataCache);
  }

  private <R1, F1> AndLoadImpl<R1, F1> andLoad(CacheRequest.Node node) {
    return new AndLoadImpl<>(node, _dataCache, _fieldHolder);
  }

  private final Object _param;

  private final CacheContainer _dataCache;
  private final CacheRequest _cacheReq;

  private final ResultFieldProxy _fieldHolder;
}
