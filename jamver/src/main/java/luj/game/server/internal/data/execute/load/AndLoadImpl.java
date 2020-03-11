package luj.game.server.internal.data.execute.load;

import java.util.function.Function;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.data.GameDataLoad.AndLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class AndLoadImpl<R, F> implements GameDataLoad.AndLoad<R, F> {

  AndLoadImpl(CacheRequest.Node node) {
    _node = node;
  }

  @Override
  public AndLoad load(Function<F, ?> from, Function<R, ?> to) {
    new LoadRequestFieldAppender(_node, (Function<Object, ?>) to, null, null).append();
    return null;
  }

  private static final Logger LOG = LoggerFactory.getLogger(AndLoadImpl.class);

  private final CacheRequest.Node _node;
}
