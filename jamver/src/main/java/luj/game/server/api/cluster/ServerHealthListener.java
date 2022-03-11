package luj.game.server.api.cluster;


import java.util.Set;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;

public interface ServerHealthListener {

  interface Context {

    Server remoteServer();

    Service service();
  }

  interface Server {

    String id();

    Set<String> tags();

    boolean isHealthy();
  }

  interface Service {

    Data data();
  }

  interface Data {

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);
  }

  void onHandle(Context ctx) throws Exception;
}
