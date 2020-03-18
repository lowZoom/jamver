package luj.game.server.api.plugin;

import java.util.Map;

public interface JamverDataSaveCreate {

  interface Context {

    <T> T getSaveState();

    Class<?> getDataType();

    Map<String, Object> getDataValue();
  }

  void onCreate(Context ctx);
}
