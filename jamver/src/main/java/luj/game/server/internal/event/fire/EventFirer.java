package luj.game.server.internal.event.fire;

import java.util.function.BiConsumer;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.event.instance.EventBuilderProxy;
import luj.game.server.internal.event.instance.EventInstanceCreator;
import luj.game.server.internal.event.instance.EventInstanceProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.fire.FireEventMsg;

/**
 * @see EventFirerV2
 */
@Deprecated
public class EventFirer {

  public EventFirer(Class<?> eventType, BiConsumer<?, ?> builder,
      ActorPreStartHandler.Actor eventRef) {
    _eventType = eventType;
    _builder = builder;
    _eventRef = eventRef;
  }

  public void fire() {
    EventInstanceProxy instanceProxy = new EventInstanceCreator(_eventType).create();
    EventBuilderProxy builderProxy = new EventBuilderProxy(instanceProxy).init(_eventType);

    Object builder = builderProxy.getInstance();
    ((BiConsumer<Object, Object>) _builder).accept(new FieldImpl(builderProxy), builder);

    Object event = instanceProxy.getInstance();
    _eventRef.tell(new FireEventMsg(event, _eventType));
  }

  private final Class<?> _eventType;
  private final BiConsumer<?, ?> _builder;

  private final Tellable _eventRef;
}
