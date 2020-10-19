package luj.game.server.api.plugin;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface JamverDataSaveIo<S> {

  interface Context {

    <T> T getSaveState(JamverDataSaveIo<T> plugin);

    Collection<Created> getCreated();

    Collection<Changed> getChanged();
  }

  interface Id {

    Object value();

    String fieldName();
  }

  //////////////////////////////

  interface Created {

    Class<?> dataType();

    Id dataId();

    Map<String, Object> primitive();

    Map<String, Set<Object>> set();

    Map<String, Map<Object, Object>> map();
  }

  //////////////////////////////

  interface Changed {

    Class<?> dataType();

    Id dataId();

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

  //////////////////////////////

  void onIo(Context ctx);
}
