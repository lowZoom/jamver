package luj.game.server.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import org.springframework.stereotype.Component;

public interface GameEventListener<E> {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Component
  @interface Register {
    // NOOP
  }

  interface Context {

    <E> E event(GameEventListener<E> listener);

    Service service();
  }

  interface Service {

    Data data();
  }

  interface Data {

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);
  }

  void onEvent(Context ctx) throws Exception;
}
