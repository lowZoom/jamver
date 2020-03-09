package luj.game.server.api.cluster;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

public interface ServerJoinListener {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Component
  @interface Register {
    // NOOP
  }

  interface Context {

    Server remoteServer();
  }

  interface Server {

    void sendMessage(Object msg);
  }

  void onHandle(Context ctx) throws Exception;
}
