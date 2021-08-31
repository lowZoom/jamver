package luj.game.server.api.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.api.net.GameHttpHandler;
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

    DataCommandException error(String messageTemplate, Object... messageArgs);

    Logger logger();

    Service service();
  }

  interface Service {

    Data data();

    Proto proto();

    <C> Config<C> config(Class<C> configType);

    Event event();

    Network network();

    Random random();

    Time time();
  }

  ///////////////////////////////////////////////////////

  interface Data {

    interface Field<V> {

      void $(V value);
    }

    <T> T create(Class<T> dataType);

    /**
     * @see #set(Supplier)
     */
    @Deprecated
    <T> void set(Supplier<T> field, T value);

    <T> Field<T> set(Supplier<T> field);

    <T extends Comparable<T>> T id(Object data);

    boolean exists(Object data);

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);

    //TODO: 准备弃用
    <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType);
  }
  //----------------------------------------------------

  interface Proto {

    <T> T create(Class<T> protoType, BiConsumer<CommandService.Param, T> protoValue);
  }
  //----------------------------------------------------

  interface Config<C> {

    C find(Comparable<?> id);

    /**
     * @see #all
     */
    @Deprecated
    Collection<C> list();

    Iterable<C> all();

    Stream<C> allStream();
  }
  //----------------------------------------------------

  interface Event {

    interface Field {

      <T> Field set(Supplier<T> field, T value);
    }

    <T> void fire(Class<T> eventType, BiConsumer<Field, T> builder);
  }
  //----------------------------------------------------

  interface Network {

    Session session();

    /**
     * @see #session()
     */
    @Deprecated
    Session session(Comparable<?> sessionId);

    Server server();

    Http http();
  }

  interface Session {

    void send(Object proto);
  }

  interface Server {

    <T> void send(Class<T> msgType, BiConsumer<CommandService.Param, T> msgValue);
  }

  interface Http {

    void request(String url, Map<String, Object> params,
        Class<? extends GameHttpHandler<?>> handler);
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
