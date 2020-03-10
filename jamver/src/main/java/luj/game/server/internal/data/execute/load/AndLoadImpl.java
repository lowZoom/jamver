package luj.game.server.internal.data.execute.load;

import java.util.function.Function;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.data.GameDataLoad.AndLoad;

final class AndLoadImpl<R, F> implements GameDataLoad.AndLoad<R, F> {

  @Override
  public AndLoad load(Function<F, ?> a, Function<R, ?> b) {
    return null;
  }
}
