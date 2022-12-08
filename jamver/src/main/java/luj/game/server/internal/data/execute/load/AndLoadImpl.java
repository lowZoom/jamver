package luj.game.server.internal.data.execute.load;

import java.util.Collection;
import java.util.function.Function;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOpFactory;
import luj.game.server.internal.data.execute.load.result.ResultFieldProxy;

final class AndLoadImpl<R, F> implements GameDataLoad.AndLoad<R, F> {

  AndLoadImpl(CacheRequest.Node node, CacheContainer dataCache, ResultFieldProxy fieldHolder) {
    _node = node;
    _dataCache = dataCache;
    _fieldHolder = fieldHolder;
  }

  @Override
  public <ID extends Comparable<ID>, F2> GameDataLoad.AndLoad<R, F2> join(Function<F, ID> from,
      Function<R, F2> to) {
    LoadNodeOp op = opFactory().createGetOne(from);
    CacheRequest.Node child = ReqChildFieldAppender.GET
        .appendV2(_node, (Function<Object, ?>) to, _fieldHolder, op);
    return andLoad(child);
  }

  @Override
  public <ID extends Comparable<ID>, F2> GameDataLoad.AndLoad<R, F2> joinAll(
      Function<F, Collection<ID>> from, Function<R, Collection<F2>> to) {
    LoadNodeOp op = opFactory().createGetMulti(from);
    CacheRequest.Node child = ReqChildFieldAppender.GET
        .appendV2(_node, (Function<Object, ?>) to, _fieldHolder, op);
    return andLoad(child);
  }

  @Override
  public <ID extends Comparable<ID>, F2> GameDataLoad.AndLoad<R, F2> join(Function<F, ID> from,
      Class<F2> dataType) {
    LoadNodeOp op = opFactory().createGetOne(from);
    CacheRequest.Node child = ReqChildTransientAppender.GET.appendV2(_node, dataType, op);
    return andLoad(child);
  }

  private LoadNodeOpFactory opFactory() {
    return new LoadNodeOpFactory(_dataCache);
  }

  private <R1, F1> AndLoadImpl<R1, F1> andLoad(CacheRequest.Node node) {
    return new AndLoadImpl<>(node, _dataCache, _fieldHolder);
  }

  //  private static final Logger LOG = LoggerFactory.getLogger(AndLoadImpl.class);

  private final CacheRequest.Node _node;

  private final CacheContainer _dataCache;
  private final ResultFieldProxy _fieldHolder;
}
