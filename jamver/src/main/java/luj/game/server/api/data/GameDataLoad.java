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

    <R, F> AndLoad<R, F> loadGlobal(GameDataLoad<?, R> load, Class<F> dataType);

    <D, R> void find(Class<D> dataType, Consumer<FindCondition<D>> condition,
        Function<R, Collection<?>> resultField);
  }

  interface AndLoad<R, F> {

    /**
     * @see #join
     */
    @Deprecated
    AndLoad load(Function<F, ?> from, Function<R, ?> to);

    AndLoad join(Function<F, ?> from, Function<R, ?> to);
  }

  void onLoad(Context ctx);
}
