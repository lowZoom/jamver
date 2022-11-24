package luj.game.server.internal.luj.lujcluster.actor.gameplay.event.fire;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.internal.event.listener.invoke.EventListenInvoker;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;
import luj.spring.anno.Internal;

@Internal
final class OnFireEvent implements GameplayEventActor.Handler<FireEventMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayEventActor actor = ctx.getActorState(this);
    FireEventMsg msg = ctx.getMessage(this);

    Class<?> eventType = msg.getEventType();
    List<GameEventListener<?>> listenerList = actor.getListenerMap()
        .getOrDefault(eventType.getName(), ImmutableList.of());

    if (listenerList.isEmpty()) {
      return;
    }

    Tellable dataRef = actor.getSiblingRef().getDataRef();

    new EventListenInvoker(listenerList, msg.getEvent(),
        dataRef, actor.getCommandMap(), actor.getLujbean()).invoke();
  }
}
