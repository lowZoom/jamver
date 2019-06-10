package luj.game.server.api.boot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

public interface GameStartListener {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Component
  @interface Register {
    // NOOP
  }

  interface Context {

  }

  void onStart(Context ctx) throws Exception;
}
