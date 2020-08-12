package luj.game.server.api.data.service;

import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface CommandService<P> {

  interface Param {

    <V> Field<V> set(Supplier<V> field);
  }

  interface Field<V> {

    Param $(V value);
  }

  void execute(BiConsumer<Param, P> param);

  void schedule(Duration delay);

  void cancelSchedule();
}
