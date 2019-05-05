package luj.game.server.api.data;

import java.util.function.Function;

public interface GameDataLoad<P, R> {

  interface Context {

    <P> P param(GameDataLoad<P, ?> load);

    @Deprecated
    <R, F> AndLoad<R, F> load(Function<R, F> field, Comparable<?> id);

    <R, F> AndLoad<R, F> load(Function<R, F> field, Class<F> dataType, Comparable<?> dataId);
  }

  interface AndLoad<R, F> {

  }

  void onLoad(Context ctx);
}
