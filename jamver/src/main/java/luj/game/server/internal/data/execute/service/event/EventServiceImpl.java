package luj.game.server.internal.data.execute.service.event;

import java.util.function.BiFunction;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.event.fire.EventFirerV2;

final class EventServiceImpl<E> implements GameDataCommand.Event<E> {

  @Override
  public void fire(BiFunction<Instance, E, Instance> e) {
    new EventFirerV2<>(_eventType, _lujbean, e, _eventRef).fire();
  }

  Class<?> _eventType;
  BeanContext _lujbean;

  Tellable _eventRef;
}
