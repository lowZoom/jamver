package luj.game.server.internal.luj.lujcluster;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import luj.ava.spring.Internal;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.boot.game.StartTrigger;
import luj.game.server.internal.cluster.handle.collect.ClusterHandleMapCollector;
import luj.game.server.internal.data.command.collect.CommandMapCollector;
import luj.game.server.internal.data.command.collect.group.GroupMapCollector;
import luj.game.server.internal.event.listener.collect.EventListenerMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;

@Internal
final class OnLujclusterStart implements NodeStartListener {

  /**
   * @see luj.game.server.internal.framework.ServerInstanceStarter#start
   */
  @Override
  public void onStart(Context ctx) throws Exception {
    JambeanInLujcluster param = ctx.getStartParam();

    Actor dataRef = ctx.createApplicationActor(dataActor(param, param.getAppStartParam()));
    ctx.createApplicationActor(eventActor(param, dataRef));
    ctx.createApplicationActor(clusterActor(param, dataRef));

    //TODO: 异步后面再执行
    new StartTrigger(param.getStartListenerList(), dataRef).trigger();
  }

  private GameplayDataActor dataActor(JambeanInLujcluster clusterParam, Object jamverParam) {
    CacheSession lujcache = clusterParam.getLujcache();
    CacheContainer dataCache = lujcache.createCache(null);

    Map<Class<?>, GameplayDataActor.CommandKit> cmdMap = new CommandMapCollector(
        clusterParam.getDataCommandList(), clusterParam.getDataLoadList()).collect();

    Map<Class<?>, GameplayDataActor.GroupKit> groupMap = new GroupMapCollector(
        clusterParam.getCommandGroupList()).collect();

    return new GameplayDataActor(dataCache, new LinkedList<>(), lujcache, cmdMap,
        groupMap, clusterParam.getDataAllPlugin(), jamverParam);
  }

  private GameplayEventActor eventActor(JambeanInLujcluster param, Actor dataRef) {
    return new GameplayEventActor(new EventListenerMapCollector(
        param.getEventListenerList()).collect(), param.getEventListenService(), dataRef);
  }

  private ClusterCommActor clusterActor(JambeanInLujcluster param, Actor dataRef) {
    List<ServerMessageHandler<?>> handlerList = param.getClusterMessageList();
    return new ClusterCommActor(new ClusterHandleMapCollector(handlerList).collect(),
        param.getClusterProtoPlugin(), dataRef);
  }
}
