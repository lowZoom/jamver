package luj.game.server.internal.data.execute.load;

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
    return join(from, to);
  }

  @Override
  public GameDataLoad.AndLoad join(Function<F, ?> from, Function<R, ?> to) {
    new ReqChildFieldAppender(_node, (Function<Object, ?>) to, _fieldHolder, from).append();
    return null;
  }

  //  private static final Logger LOG = LoggerFactory.getLogger(AndLoadImpl.class);

  private final CacheRequest.Node _node;

  private final ResultFieldProxy _fieldHolder;
}
