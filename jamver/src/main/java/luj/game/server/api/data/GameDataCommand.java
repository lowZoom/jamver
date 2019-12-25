package luj.game.server.api.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

public interface GameDataCommand<P, D> {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
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

    <C> Config<C> config(Class<C> configType);

    Random random();

    Network network();
  }

  interface Data {

    <T> T create(Class<T> dataType);
  }

  interface Config<C> {

    C find(Comparable<?> id);

    Collection<C> list();
  }

  interface Random {

    boolean randBool(double likelihood);
  }

  interface Network {

    Session session(Comparable<?> sessionId);
  }

  interface Session {

    void send(Object proto);
  }

  void onExecute(Context ctx) throws Exception;
}
