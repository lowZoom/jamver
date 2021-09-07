package luj.game.server.internal.event.listener.collect;

import static java.util.stream.Collectors.groupingBy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import luj.ava.reflect.type.TypeX;
import luj.ava.stream.StreamX;
import luj.game.server.api.event.GameEventListener;

public class EventListenerMapCollector {

  public EventListenerMapCollector(List<GameEventListener<?>> listenerList) {
    _listenerList = listenerList;
  }

  public Map<String, List<GameEventListener<?>>> collect() {
    return StreamX.from(_listenerList)
        .collect(groupingBy(l -> getEventType(l).getName()));
  }

  private Class<?> getEventType(GameEventListener<?> listener) {
    return TypeX.ofInstance(listener)
        .getSupertype(GameEventListener.class)
        .getTypeParam(0)
        .asClass();
  }

  private final List<GameEventListener<?>> _listenerList;
}
