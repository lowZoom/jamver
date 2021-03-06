package luj.game.server.api.net;

import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;

public interface GameAcceptHandler {

  interface Context {

    Connection connection();

    Address getBindAddress();

    Service service();
  }

  interface Connection {

    Integer id();

    Address remoteAddress();

    void send(Object proto);

    void close();
  }

  interface Address {

    String host();

    int port();
  }

  ////////////////////////////////////////

  interface Service {

    Data data();
  }

  interface Data {

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);
  }

  ////////////////////////////////////////

  void onHandle(Context ctx);
}
