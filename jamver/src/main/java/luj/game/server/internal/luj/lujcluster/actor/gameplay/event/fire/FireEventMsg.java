package luj.game.server.internal.luj.lujcluster.actor.gameplay.event.fire;

public class FireEventMsg {

  public FireEventMsg(Object event, Class<?> eventType) {
    _event = event;
    _eventType = eventType;
  }

  public Object getEvent() {
    return _event;
  }

  public Class<?> getEventType() {
    return _eventType;
  }

  private final Object _event;

  private final Class<?> _eventType;
}
