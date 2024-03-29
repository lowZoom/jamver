package luj.game.server.api.data;

import java.util.Collection;
import java.util.function.Function;

public interface GameDataLoad<P, R> {

  interface Context {

    <P> P param(GameDataLoad<P, ?> load);

    <R, F> AndLoad<R, F> load(Comparable<?> id, Function<R, F> result);

//    <D, DF, R, RF> AndLoad<R, RF> load(Function<D, DF> field, DF value, Function<R, RF> result);

    <R, F> AndLoad<R, F> load(GameDataLoad<?, R> load, Comparable<?> id, Class<F> dataType);

    <R, F> AndLoad<R, F> loadAll(Collection<? extends Comparable<?>> id, Function<R, Collection<F>> result);

    <R, F> AndLoad<R, F> loadGlobal(Function<R, F> field);

    <R, F> AndLoad<R, F> loadGlobal(GameDataLoad<?, R> load, Class<F> dataType);

    //    Class<D> dataType,
//    <D, R> void findAll(Consumer<FindCondition<D>> condition, Function<R, Collection<D>> field);
  }

  interface AndLoad<R, F> {

    <ID extends Comparable<ID>, F2> AndLoad<R, F2> join(Function<F, ID> from, Function<R, F2> to);

    <ID extends Comparable<ID>, F2> AndLoad<R, F2> joinAll(
        Function<F, Collection<ID>> from, Function<R, Collection<F2>> to);

    <ID extends Comparable<ID>, F2> AndLoad<R, F2> join(Function<F, ID> from, Class<F2> dataType);
  }

  void onLoad(Context ctx);
}
