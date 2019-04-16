package luj.game.server.api.data;

public interface GameDataCommand<P, D> {

  @interface Register {

  }

  interface Context {

    <P> P param(GameDataCommand<P, ?> cmd);

    <D> D data(GameDataCommand<?, D> cmd);

    Service service();
  }

  interface Service {

    Data data();
  }

  interface Data {

    <T> T create(Class<T> dataType);
  }

  void onExecute(Context ctx);
}
