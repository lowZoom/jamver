package luj.game.server.api.data.find;

import java.util.function.Function;

public interface FindCondition<D> {

  interface Field<T> {

    void eq(T value);
  }

  <F> Field<F> field(Function<D, F> field);
}
