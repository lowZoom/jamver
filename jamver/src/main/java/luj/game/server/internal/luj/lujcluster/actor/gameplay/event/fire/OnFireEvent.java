package luj.game.server.internal.luj.lujcluster.actor.gameplay.event.fire;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.ava.spring.Internal;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;

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

    Object event = msg.getEvent();
    ListenerContextImpl listenerCtx = new ListenerContextImpl(event, actor.getListenService());

    for (GameEventListener<?> listener : listenerList) {
      listener.onEvent(listenerCtx);
    }
  }
}
