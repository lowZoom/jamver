package luj.game.server.api.cluster;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;
import java.util.function.BiFunction;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import org.springframework.stereotype.Component;

public interface ServerJoinListener {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Component
  @interface Register {
    // NOOP
  }

  interface Context {

    Server remoteServer();

    Service service();
  }

  interface Server {

    String name();

    Set<String> tags();

    void sendMessage(Object msg);

    <T> void sendMessage(Class<T> msgType,
        BiFunction<CommandService.Param, T, CommandService.Param> msgValue);
  }

  interface Service {

    Data data();
  }

  interface Data {

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);
  }

  void onHandle(Context ctx) throws Exception;
}
