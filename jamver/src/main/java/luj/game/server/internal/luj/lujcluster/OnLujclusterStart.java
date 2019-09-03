package luj.game.server.internal.luj.lujcluster;

import java.util.List;
import luj.ava.spring.Internal;
import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.boot.StartTrigger;
import luj.game.server.internal.cluster.handle.collect.ClusterHandleMapCollector;
import luj.game.server.internal.event.listener.collect.EventListenerMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;
import org.springframework.beans.factory.annotation.Autowired;

@Internal
final class OnLujclusterStart implements NodeStartListener {

  @Override
  public void onStart(Context ctx) throws Exception {
    JambeanInLujcluster param = ctx.getStartParam();

    ctx.createApplicationActor(eventActor(param));
    ctx.createApplicationActor(clusterActor(param));

    new StartTrigger(_startListenerList).trigger();
  }

  private GameplayEventActor eventActor(JambeanInLujcluster param) {
    return new GameplayEventActor(new EventListenerMapCollector(
        param.getEventListenerList()).collect(), param.getEventListenService());
  }

  private ClusterCommActor clusterActor(JambeanInLujcluster param) {
    List<ServerMessageHandler<?>> handlerList = param.getClusterHandlerList();
    return new ClusterCommActor(new ClusterHandleMapCollector(handlerList).collect());
  }

  @Autowired
  private List<GameStartListener> _startListenerList;
}
