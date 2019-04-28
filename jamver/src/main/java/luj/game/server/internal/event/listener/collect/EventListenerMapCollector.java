package luj.game.server.internal.event.listener.collect;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import luj.ava.reflect.type.TypeX;
import luj.ava.stream.StreamX;
import luj.game.server.api.event.GameEventListener;

public class EventListenerMapCollector {

  public EventListenerMapCollector(List<GameEventListener<?>> listenerList) {
    _listenerList = listenerList;
  }

  public Map<Class<?>, List<GameEventListener<?>>> collect() {
    return StreamX.from(_listenerList)
        .collect(Collectors.groupingBy(this::getEventType));
  }

  private Class<?> getEventType(GameEventListener<?> listener) {
    return TypeX.ofInstance(listener)
        .getSupertype(GameEventListener.class)
        .getTypeParam(0)
        .asClass();
  }

  private final List<GameEventListener<?>> _listenerList;
}
