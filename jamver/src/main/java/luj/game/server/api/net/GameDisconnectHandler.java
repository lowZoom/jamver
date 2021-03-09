package luj.game.server.api.net;

import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;

public interface GameDisconnectHandler {

  interface Context {

    Connection connection();

    Service service();
  }

  interface Connection {

    Integer id();
  }

  interface Service {

    Data data();
  }

  interface Data {

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);
  }

  void onHandle(Context ctx);
}
