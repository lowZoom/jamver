package luj.game.server.api.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
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

    Network network();

    Random random();

    Time time();
  }

  ///////////////////////////////////////////////////////

  interface Data {

    <T> T create(Class<T> dataType);

    <T> void set(Supplier<T> field, T value);

    //TODO: 这个接口还要再斟酌一下（关于操作集合的形式）
    <T> void add(Supplier<Collection<T>> field, T element);

    boolean exists(Object data);

    <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType);

    <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param);
  }
  //----------------------------------------------------

  interface Proto {

    <T> T create(Class<T> protoType);

    <T> void set(Supplier<T> field, T value);
  }
  //----------------------------------------------------

  interface Config<C> {

    C find(Comparable<?> id);

    Collection<C> list();
  }
  //----------------------------------------------------

  interface Random {

    boolean randBool(double likelihood);
  }
  //----------------------------------------------------

  interface Time {

    long now();
  }
  //----------------------------------------------------

  interface Network {

    Session session(Comparable<?> sessionId);

    Http http();
  }

  interface Session {

    void send(Object proto);
  }

  interface Http {

    void request(String url, Map<String, Object> params,
        Class<? extends GameHttpHandler<?>> handler);
  }

  ///////////////////////////////////////////////////////

  void onExecute(Context ctx) throws Exception;
}
