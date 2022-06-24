package luj.game.server.internal.luj.lujcluster.actor.gameplay.event.register;

import java.util.List;
import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.internal.event.listener.collect.EventListenerMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnAddEventListener implements GameplayEventActor.Handler<AddEventListenerMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayEventActor self = ctx.getActorState(this);
    AddEventListenerMsg msg = ctx.getMessage(this);

    Map<String, List<GameEventListener<?>>> addMap =
        new EventListenerMapCollector(msg.getListeners()).collect();

    LOG.debug("[game]新增GameEventListener，数量：{}", addMap.size());
    self.getListenerMap().putAll(addMap);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnAddEventListener.class);
}
