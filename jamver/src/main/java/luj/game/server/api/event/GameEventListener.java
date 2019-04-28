package luj.game.server.api.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

public interface GameEventListener<E> {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
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

    void executeCommand(Class<?> commandType, Object param);
  }

  void onEvent(Context ctx);
}
