package luj.game.server.api.net;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import org.springframework.stereotype.Component;

public interface GameProtoHandler<P> {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Component
  @interface Register {

    String value();
  }

  interface Context {

    <P> P proto(GameProtoHandler<P> handler);

    Player player();

    Service service();
  }

  interface Player {

    Long getPlayerId();

    void sendProto();
  }

  interface Service {

    <C> Config<C> config(Class<C> configType);

    Data data();
  }

  interface Config<C> {

    C find(Comparable<?> id);

    Collection<C> list();
  }

  interface Data {

    void executeCommand(Class<?> commandType, Object param);
  }

  void onHandle(Context ctx);
}
