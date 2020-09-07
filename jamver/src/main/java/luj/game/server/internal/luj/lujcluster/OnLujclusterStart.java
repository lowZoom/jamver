package luj.game.server.internal.luj.lujcluster;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import luj.ava.spring.Internal;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.cluster.handle.collect.ClusterHandleMapCollector;
import luj.game.server.internal.data.command.collect.CommandMapCollector;
import luj.game.server.internal.data.command.collect.group.GroupMapCollector;
import luj.game.server.internal.event.listener.collect.EventListenerMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;
import luj.game.server.internal.luj.lujcluster.actor.start.JamStartActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.StartRefMsg;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopRefCollection;

@Internal
final class OnLujclusterStart implements NodeStartListener {

  /**
   * @see luj.game.server.internal.framework.ServerInstanceStarter#start
   * @see luj.game.server.internal.luj.lujcluster.actor.start.OnPrestart#onHandle
   */
  @Override
  public void onStart(Context ctx) {
    JambeanInLujcluster param = ctx.getStartParam();

    Map<Class<?>, GameplayDataActor.CommandKit> cmdMap = new CommandMapCollector(
        param.getDataCommandList(), param.getDataLoadList()).collect();

    TopRefCollection allRef = new TopRefCollection(
        ctx.createApplicationActor(dataActor(param, cmdMap)),
        ctx.createApplicationActor(eventActor(param)),
        ctx.createApplicationActor(clusterActor(param, cmdMap))
    );

    List<Tellable> refList = ImmutableList
        .of(allRef.getDataRef(), allRef.getEventRef(), allRef.getClusterRef());

    CountDownLatch startLatch = new CountDownLatch(refList.size());
    StartRefMsg msg = new StartRefMsg(allRef, startLatch);
    for (Tellable ref : refList) {
      ref.tell(msg);
    }

    //FIXME: 临时用单独的actor实现，最后应该把上面的整合进来
    JamStartActor startState = new JamStartActor(startLatch, param, allRef);
    ctx.createApplicationActor(startState);
  }

  private GameplayDataActor dataActor(JambeanInLujcluster clusterParam,
      Map<Class<?>, GameplayDataActor.CommandKit> cmdMap) {
    CacheSession lujcache = clusterParam.getLujcache();
    CacheContainer dataCache = lujcache.createCache(null);

    Map<Class<?>, GameplayDataActor.GroupKit> groupMap = new GroupMapCollector(
        clusterParam.getCommandGroupList()).collect();

    return new GameplayDataActor(dataCache, new LinkedList<>(), lujcache, cmdMap,
        groupMap, clusterParam.getLujbean(), clusterParam.getDataAllPlugin(),
        clusterParam.getAppStartParam());
  }

  private GameplayEventActor eventActor(JambeanInLujcluster param) {
    return new GameplayEventActor(new EventListenerMapCollector(
        param.getEventListenerList()).collect(), param.getEventListenService());
  }

  private ClusterCommActor clusterActor(JambeanInLujcluster param,
      Map<Class<?>, GameplayDataActor.CommandKit> cmdMap) {
    Map<String, ServerMessageHandler<?>> handlerMap =
        new ClusterHandleMapCollector(param.getClusterMessageList()).collect();
    return new ClusterCommActor(ArrayListMultimap.create(),
        handlerMap, cmdMap, param.getClusterProtoPlugin(), param.getLujbean());
  }
}
