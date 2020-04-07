package luj.game.server.api.net;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import luj.game.server.api.data.GameDataCommand;
import org.springframework.stereotype.Component;

public interface GameProtoHandler<P> {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Component
  @interface Register {

    String value();
  }

  interface Context {

    <P> P proto(GameProtoHandler<P> handler);

    //FIXME: 不应该出现在框架层
    Player player();

    Service service();
  }

  interface Player {

    Long getPlayerId();

    void sendProto(Object proto);
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

    <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param);
  }

  void onHandle(Context ctx);
}
