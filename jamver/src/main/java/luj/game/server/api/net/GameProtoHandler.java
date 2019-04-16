package luj.game.server.api.net;

public interface GameProtoHandler<P> {

  @interface Register {

    String value();
  }

  interface Context {

    <P> P proto(GameProtoHandler<P> handler);

    Player player();

    Service service();
  }

  interface Player {

    Long getPlayerId();

    void sendProto();
  }

  interface Service {

    Config config(Class<?> configType);

    Data data();
  }

  interface Config {

    <C> C find(Comparable<?> id);
  }

  interface Data {

    void executeCommand(Class<?> commandType, Object param);
  }

  void onHandle(Context ctx);
}
