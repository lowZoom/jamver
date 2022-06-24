package luj.game.server.internal.event.fire;

import java.util.function.BiFunction;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.event.instancev2.EventInstanceMakerV2;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.fire.FireEventMsg;

public class EventFirerV2<E> {

  public EventFirerV2(Class<?> eventType, BeanContext lujbean,
      BiFunction<GameDataCommand.Event.Instance, E, ?> buildCaller, Tellable eventRef) {
    _eventType = eventType;
    _lujbean = lujbean;
    _buildCaller = buildCaller;
    _eventRef = eventRef;
  }

  @SuppressWarnings("unchecked")
  public void fire() {
    Object event = new EventInstanceMakerV2(_lujbean,
        _eventType, (b, e) -> _buildCaller.apply(b, (E) e)).make();

    _eventRef.tell(new FireEventMsg(event, _eventType));
  }

  private final Class<?> _eventType;

  private final BeanContext _lujbean;
  private final BiFunction<GameDataCommand.Event.Instance, E, ?> _buildCaller;

  private final Tellable _eventRef;
}
