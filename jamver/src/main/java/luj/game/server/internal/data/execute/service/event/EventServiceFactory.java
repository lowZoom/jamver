package luj.game.server.internal.data.execute.service.event;

import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.data.GameDataCommand;

public class EventServiceFactory {

  public EventServiceFactory(Tellable eventRef, BeanContext lujbean) {
    _eventRef = eventRef;
    _lujbean = lujbean;
  }

  public <E> GameDataCommand.Event<E> create(Class<E> eventType) {
    EventServiceImpl<E> svc = new EventServiceImpl<>();
    svc._eventType = eventType;

    svc._eventRef = _eventRef;
    svc._lujbean = _lujbean;

    return svc;
  }

  private final Tellable _eventRef;

  private final BeanContext _lujbean;
}
