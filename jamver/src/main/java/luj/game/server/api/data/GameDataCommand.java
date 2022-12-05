package luj.game.server.api.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;
import luj.game.server.api.data.service.CommandService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

public interface GameDataCommand<P, D> {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Component
  @interface Register {
    // NOOP
  }

  interface Context {

    <P> P param(GameDataCommand<P, ?> cmd);

    <D> D data(GameDataCommand<?, D> cmd);

    Logger logger();

    Service service();
  }

  interface Service {

    Data data();

    Proto proto();

    <C> Config<C> config(Class<C> configType);

    <E> Event<E> event(Class<E> eventType);

    Random random();

    Time time();
  }

  ///////////////////////////////////////////////////////

  interface Data {

    interface Field<V> {

      void $(V value);
    }

    <T> T create(Class<T> dataType);

    <T> Field<T> set(Supplier<T> field);

    <T extends Comparable<T>> T id(Object data);

    boolean exists(Object data);

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);
  }
  //----------------------------------------------------

  interface Proto {

    <T> T create(Class<T> protoType,
        BiFunction<CommandService.Param, T, CommandService.Param> protoValue);
  }
  //----------------------------------------------------

  interface Config<C> {

    C find(Comparable<?> id);

    C findGlobal();

    Iterable<C> all();

    Stream<C> allStream();
  }
  //----------------------------------------------------

  interface Event<E> {

    interface Instance {

      <V> Field<V> set(Supplier<V> field);
    }

    interface Field<V> {

      Instance $(V value);
    }

    void fire(BiFunction<Instance, E, Instance> e);
  }
  //----------------------------------------------------

  interface Random {

    boolean randBool(double likelihood);
  }
  //----------------------------------------------------

  interface Time {

    long now();
  }

  ///////////////////////////////////////////////////////

  void onExecute(Context ctx) throws Exception;
}
