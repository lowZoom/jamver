package luj.game.server.api.plugin;

import java.util.Map;
import java.util.Set;

public interface JamverDataSaveUpdate {

  interface Context {

    <T> T getSaveState();

    Class<?> getDataType();

    Id getDataId();

    Changed getChangedFields();
  }

  interface Id {

    Object getValue();

    String getField();
  }

  interface Changed {

    Map<String, Object> primitive();

    Map<String, SetChanged> set();

    Map<String, MapChanged> map();
  }

  interface SetChanged {

    Set<Object> added();

    Set<Object> removed();
  }

  interface MapChanged {

    Map<Object, Object> updated();

    Set<Object> removed();
  }

  void onUpdate(Context ctx);
}
