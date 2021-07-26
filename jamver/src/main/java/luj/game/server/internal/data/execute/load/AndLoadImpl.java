package luj.game.server.internal.data.execute.load;

import java.util.Collection;
import java.util.function.Function;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;

final class AndLoadImpl<R, F> implements GameDataLoad.AndLoad<R, F> {

  AndLoadImpl(CacheRequest.Node node, ResultFieldProxy fieldHolder) {
    _node = node;
    _fieldHolder = fieldHolder;
  }

  @Override
  public GameDataLoad.AndLoad load(Function<F, ?> from, Function<R, ?> to) {
    return join((Function) from, to);
  }

  @Override
  public <ID extends Comparable<ID>, F2> GameDataLoad.AndLoad<R, F2> join(Function<F, ID> from,
      Function<R, F2> to) {
    CacheRequest.Node child = ReqChildFieldAppender.GET
        .append(_node, (Function<Object, ?>) to, _fieldHolder, from);
    return new AndLoadImpl<>(child, _fieldHolder);
  }

  @Override
  public <ID extends Comparable<ID>, F2> GameDataLoad.AndLoad<R, F2> joinAll(
      Function<F, Collection<ID>> from, Function<R, Collection<F2>> to) {
    CacheRequest.Node child = ReqChildFieldAppender.GET
        .append(_node, (Function<Object, ?>) to, _fieldHolder, from);
    return new AndLoadImpl<>(child, _fieldHolder);
  }

  @Override
  public <ID extends Comparable<ID>, F2> GameDataLoad.AndLoad<R, F2> join(Function<F, ID> from,
      Class<F2> dataType) {
    CacheRequest.Node child = ReqChildTransientAppender.GET.append(_node, dataType, from);
    return new AndLoadImpl<>(child, _fieldHolder);
  }

  //  private static final Logger LOG = LoggerFactory.getLogger(AndLoadImpl.class);

  private final CacheRequest.Node _node;
  private final ResultFieldProxy _fieldHolder;
}
