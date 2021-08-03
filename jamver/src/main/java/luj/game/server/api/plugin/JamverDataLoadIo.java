package luj.game.server.api.plugin;

import java.util.Map;

public interface JamverDataLoadIo {

  interface Context {

    <T> T getLoadState();

    Data getDataRequest();
  }

  interface Data {

    Class<?> getType();

    Comparable<?> getId();

    String getIdField();
  }

  Map<String, Object> onLoad(Context ctx);
}
