package luj.game.server.api.cluster;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.service.CommandService;
import org.springframework.stereotype.Component;

public interface ServerMessageHandler<M> {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Component
  @interface Register {
    // NOOP
  }

  interface Context {

    <M> M message(ServerMessageHandler<M> handler);

    Server remoteServer();

    Service service();
  }

  interface Server {

    void sendMessage(Object msg);
  }

  interface Service {

    Data data();
  }

  interface Data {

    interface Group {

      <P> Group command(Class<? extends GameDataCommand<P, ?>> commandType, P param);
    }

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);

    /**
     * @see #command
     */
    @Deprecated
    <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param);

    void executeCommandGroup(Class<? extends GameDataCommandGroup> type, Consumer<Group> group);
  }

  void onHandle(Context ctx) throws Exception;
}
