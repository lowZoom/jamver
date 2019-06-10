package luj.game.server.internal.luj.lujcluster;

import java.util.List;
import luj.ava.spring.Internal;
import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.internal.boot.StartTrigger;
import luj.game.server.internal.event.listener.collect.EventListenerMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;
import org.springframework.beans.factory.annotation.Autowired;

@Internal
final class OnLujclusterStart implements NodeStartListener {

  @Override
  public void onStart(Context ctx) throws Exception {
    JambeanInLujcluster param = ctx.getStartParam();

    ctx.createApplicationActor(eventActor(param));

    new StartTrigger(_startListenerList).trigger();
  }

  private GameplayEventActor eventActor(JambeanInLujcluster param) {
    List<GameEventListener<?>> listenerList = param.getEventListenerList();
    return new GameplayEventActor(new EventListenerMapCollector(listenerList)
        .collect(), param.getEventListenService());
  }

  @Autowired
  private List<GameStartListener> _startListenerList;
}
