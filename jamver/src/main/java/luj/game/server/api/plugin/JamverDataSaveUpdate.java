package luj.game.server.api.plugin;

import java.util.Map;

public interface JamverDataSaveUpdate {

  interface Context {

    <T> T getSaveState();

    Class<?> getDataType();

    Id getDataId();

    Map<String, Object> getDataValue();
  }

  interface Id {

    Object getValue();

    String getField();
  }

  void onUpdate(Context ctx);
}
