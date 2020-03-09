package luj.game.server.internal.luj.lujcluster;

import java.util.List;
import luj.ava.spring.Internal;
import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.boot.StartTrigger;
import luj.game.server.internal.cluster.handle.collect.ClusterHandleMapCollector;
import luj.game.server.internal.event.listener.collect.EventListenerMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;

@Internal
final class OnLujclusterStart implements NodeStartListener {

  /**
   * @see luj.game.server.internal.framework.ServerInstanceStarter#start
   */
  @Override
  public void onStart(Context ctx) throws Exception {
    JambeanInLujcluster param = ctx.getStartParam();

    ctx.createApplicationActor(eventActor(param));
    ctx.createApplicationActor(clusterActor(param));

    new StartTrigger(param.getStartListenerList()).trigger();
  }

  private GameplayEventActor eventActor(JambeanInLujcluster param) {
    return new GameplayEventActor(new EventListenerMapCollector(
        param.getEventListenerList()).collect(), param.getEventListenService());
  }

  private ClusterCommActor clusterActor(JambeanInLujcluster param) {
    List<ServerMessageHandler<?>> handlerList = param.getClusterMessageList();
    return new ClusterCommActor(new ClusterHandleMapCollector(handlerList).collect());
  }
}
