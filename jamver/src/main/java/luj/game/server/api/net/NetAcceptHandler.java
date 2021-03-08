package luj.game.server.api.net;

import java.util.function.Supplier;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;

public interface NetAcceptHandler {

  interface Context {

    Connection connection();

    Address getBindAddress();

    Service service();
  }

  interface Connection {

    interface Proto {

      <V> Field<V> set(Supplier<V> field);
    }

    interface Field<V> {

      Proto $(V value);
    }

    Integer id();

    <P> void send(Object proto);
  }

  interface Address {

    String host();

    int port();
  }

  interface Service {

    Data data();
  }

  interface Data {

    <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType);
  }

  void onHandle(Context ctx);
}
