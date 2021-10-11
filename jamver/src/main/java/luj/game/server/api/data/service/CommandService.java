package luj.game.server.api.data.service;

import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public interface CommandService<P> {

  interface Param {

    <V> Field<V> set(Supplier<V> field);
  }

  interface Field<V> {

    Param $(V value);
  }

  /**
   * @see #execute
   */
  @Deprecated
  void execute0(BiConsumer<Param, P> param);

  void execute(BiFunction<Param, P, Param> param);

  void schedule(Duration delay);

  void schedule(Duration delay, BiFunction<Param, P, Param> param);

  void schedule(Comparable<?> id, BiFunction<Param, P, Param> param, Duration delay);

//  void unschedule(Comparable<?> id);

  void cancelSchedule();
}
