package luj.game.server.internal.luj.lujcluster.actor.gameplay.event.fire;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.ava.spring.Internal;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.EventActorMsgHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;

@Internal
final class OnFireEvent implements EventActorMsgHandler<FireEventMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayEventActor actor = ctx.getActor(this);
    FireEventMsg msg = ctx.getMessage(this);
    Class<?> eventType = msg.getEventType();

    List<GameEventListener<?>> listenerList = actor.getListenerMap()
        .getOrDefault(eventType, ImmutableList.of());

    ListenerContextImpl listenerCtx = new ListenerContextImpl(msg.getEvent());
    for (GameEventListener<?> listener : listenerList) {
      listener.onEvent(listenerCtx);
    }
  }
}
