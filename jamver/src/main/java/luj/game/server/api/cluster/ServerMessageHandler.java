package luj.game.server.api.cluster;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import luj.game.server.api.data.GameDataCommand;
import org.springframework.stereotype.Component;

public interface ServerMessageHandler<M> {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Component
  @interface Register {
    // NOOP
  }

  interface Context {

    @Deprecated
    <M> M getMessage(ServerMessageHandler<M> handler);

    <M> M message(ServerMessageHandler<M> handler);

    @Deprecated
    Server getRemoteServer();

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

    <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param);
  }

  void onHandle(Context ctx) throws Exception;
}
