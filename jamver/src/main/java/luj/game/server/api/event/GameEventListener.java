package luj.game.server.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.BiConsumer;
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

    /**
     * @deprecated 准备移除
     */
    @Deprecated
    Network network();
  }

  interface Data {

    /**
     * @see #command
     */
    @Deprecated
    <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param);

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);
  }

  @Deprecated
  interface Network {

    Server server();
  }

  interface Server {

    <T> void send(Class<T> msgType, BiConsumer<CommandService.Param, T> msgValue);
  }

  void onEvent(Context ctx) throws Exception;
}
