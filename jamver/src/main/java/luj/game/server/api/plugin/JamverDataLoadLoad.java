package luj.game.server.api.plugin;

public interface JamverDataLoadLoad {

  interface Context {

    <T> T getLoadState();

    Data getDataRequest();
  }

  interface Data {

    Class<?> getType();

    Comparable<?> getId();

    String getIdField();
  }

  void onLoad(Context ctx);
}
