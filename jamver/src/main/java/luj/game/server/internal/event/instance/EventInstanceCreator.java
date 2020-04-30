package luj.game.server.internal.event.instance;

import java.util.HashMap;

public class EventInstanceCreator {

  public EventInstanceCreator(Class<?> eventType) {
    _eventType = eventType;
  }

  public EventInstanceProxy create() {
    return new EventInstanceProxy(new HashMap<>()).init(_eventType);
  }

  private final Class<?> _eventType;
}
