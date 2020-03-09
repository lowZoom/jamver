package luj.game.server.api.data;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import luj.game.server.api.data.find.FindCondition;

public interface GameDataLoad<P, R> {

  interface Context {

    <P> P param(GameDataLoad<P, ?> load);

    <R, F> AndLoad<R, F> load(Function<R, F> field, Comparable<?> id);

    <R, F> AndLoad<R, F> loadGlobal(Function<R, F> field);

    <D, R> void find(Class<D> dataType, Consumer<FindCondition<D>> condition,
        Function<R, Collection<?>> resultField);
  }

  interface AndLoad<R, F> {

    AndLoad load(Function<F, ?> a, Function<R, ?> b);
  }

  void onLoad(Context ctx);
}
