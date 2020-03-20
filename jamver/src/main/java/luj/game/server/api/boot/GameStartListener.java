package luj.game.server.api.boot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import luj.game.server.api.data.GameDataCommand;
import org.springframework.stereotype.Component;

public interface GameStartListener {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Component
  @interface Register {
    // NOOP
  }

  interface Context {

    Service service();
  }

  interface Service {

    Data data();
  }

  interface Data {

    void executeCommand(Class<? extends GameDataCommand<?, ?>> commandType);
  }

  void onStart(Context ctx) throws Exception;
}
