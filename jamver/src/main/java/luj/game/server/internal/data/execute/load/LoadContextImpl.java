package luj.game.server.internal.data.execute.load;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.data.find.FindCondition;
import luj.game.server.internal.data.instance.DataTempProxy;

final class LoadContextImpl implements GameDataLoad.Context {

  LoadContextImpl(Object param, CacheRequest cacheReq, ResultFieldProxy fieldHolder) {
    _param = param;
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
    CacheRequest.Node node = new ReqRootFieldAppender(_cacheReq,
        (Function<Object, ?>) field, _fieldHolder, id).append();

    return new AndLoadImpl<>(node, _fieldHolder);
  }

  @Override
  public <R, F> GameDataLoad.AndLoad<R, F> load(GameDataLoad<?, R> load, Class<F> dataType,
      Comparable<?> id) {
    CacheRequest.Node node = ReqRootTransientAppender.GET.append(_cacheReq, dataType, id);
    return new AndLoadImpl<>(node, _fieldHolder);
  }

  @Override
  public <R, F> GameDataLoad.AndLoad<R, F> loadGlobal(Function<R, F> field) {
    CacheRequest.Node node = new ReqRootFieldAppender(_cacheReq,
        (Function<Object, ?>) field, _fieldHolder, DataTempProxy.GLOBAL).append();

    return new AndLoadImpl<>(node, _fieldHolder);
  }

  @Override
  public <R, F> GameDataLoad.AndLoad<R, F> loadGlobal(GameDataLoad<?, R> load, Class<F> dataType) {
    CacheRequest.Node node = ReqRootTransientAppender.GET
        .append(_cacheReq, dataType, DataTempProxy.GLOBAL);

    return new AndLoadImpl<>(node, _fieldHolder);
  }

  @Override
  public <D, R> void find(Class<D> dataType, Consumer<FindCondition<D>> condition,
      Function<R, Collection<?>> resultField) {

  }

  private final Object _param;

  private final CacheRequest _cacheReq;
  private final ResultFieldProxy _fieldHolder;
}
