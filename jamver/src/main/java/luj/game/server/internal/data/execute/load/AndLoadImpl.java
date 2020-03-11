package luj.game.server.internal.data.execute.load;

import java.util.function.Function;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.data.GameDataLoad.AndLoad;

final class AndLoadImpl<R, F> implements GameDataLoad.AndLoad<R, F> {

  AndLoadImpl(CacheRequest.Node node, ResultFieldProxy fieldHolder) {
    _node = node;
    _fieldHolder = fieldHolder;
  }

  @Override
  public AndLoad load(Function<F, ?> from, Function<R, ?> to) {
    new ReqChildFieldAppender(_node, (Function<Object, ?>) to, _fieldHolder, from).append();
    return null;
  }

//  private static final Logger LOG = LoggerFactory.getLogger(AndLoadImpl.class);

  private final CacheRequest.Node _node;

  private final ResultFieldProxy _fieldHolder;
}
