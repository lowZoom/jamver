package luj.game.server.internal.luj.lujcluster;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import luj.ava.spring.Internal;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.cluster.handle.collect.ClusterHandleMapCollector;
import luj.game.server.internal.data.command.collect.CommandMapCollector;
import luj.game.server.internal.data.command.collect.group.GroupMapCollector;
import luj.game.server.internal.event.listener.collect.EventListenerMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.dynamic.DynamicRootActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.DataActorFactory;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.luj.lujcluster.actor.start.JamStartActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.StartRefMsg;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
import luj.game.server.internal.network.proto.handle.collect.ProtoHandlerMapCollector;

@Internal
final class OnNodeStart implements NodeStartListener {

  /**
   * @see luj.game.server.internal.framework.ServerInstanceStarter#start
   * @see luj.game.server.internal.luj.lujcluster.actor.start.OnPrestart#onHandle
   */
  @Override
  public void onStart(Context ctx) throws Exception {
    JambeanInLujcluster param = ctx.getStartParam();

    Map<String, GameplayDataActor.CommandKit> cmdMap = new ConcurrentHashMap<>(
        new CommandMapCollector(param.getDataCommandList(), param.getDataLoadList()).collect());

    Map<String, GameProtoHandler<?>> handlerMap = new ProtoHandlerMapCollector(
        param.getProtoHandlerList()).collect();

    TopLevelRefs allRef = new TopLevelRefs(
        ctx.createApplicationActor(dataActor(param, cmdMap)),
        ctx.createApplicationActor(eventActor(param)),
        ctx.createApplicationActor(clusterActor(param, cmdMap)),
        ctx.createApplicationActor(networkActor(param, handlerMap, cmdMap)),
        ctx.createApplicationActor(dynamicActor(param))
    );

    List<Tellable> refList = ImmutableList.of(allRef.getDataRef(), allRef.getEventRef(),
        allRef.getClusterRef(), allRef.getNetworkRef(), allRef.getDynamicRef());

    CountDownLatch startLatch = new CountDownLatch(refList.size());
    StartRefMsg msg = new StartRefMsg(allRef, startLatch);
    for (Tellable ref : refList) {
      ref.tell(msg);
    }

    startLatch.await();

    //FIXME: 临时用单独的actor实现，最后应该把上面的整合进来
    JamStartActor startState = new JamStartActor(param, allRef, cmdMap);
    ctx.createApplicationActor(startState);
  }

  private GameplayDataActor dataActor(JambeanInLujcluster clusterParam,
      Map<String, GameplayDataActor.CommandKit> cmdMap) {
    CacheSession lujcache = clusterParam.getLujcache();
    CacheContainer dataCache = lujcache.createCache();

    Map<Class<?>, GameplayDataActor.GroupKit> groupMap = new GroupMapCollector(
        clusterParam.getCommandGroupList()).collect();

    return new DataActorFactory(clusterParam, dataCache, cmdMap, groupMap).create();
  }

  private GameplayEventActor eventActor(JambeanInLujcluster clusterParam) {
    return new GameplayEventActor(new EventListenerMapCollector(
        clusterParam.getEventListenerList()).collect(), clusterParam.getEventListenService());
  }

  private ClusterCommActor clusterActor(JambeanInLujcluster clusterParam,
      Map<String, GameplayDataActor.CommandKit> cmdMap) {
    Map<String, ServerMessageHandler<?>> handlerMap =
        new ClusterHandleMapCollector(clusterParam.getClusterMessageList()).collect();
    return new ClusterCommActor(ArrayListMultimap.create(),
        handlerMap, cmdMap, clusterParam.getClusterProtoPlugin(), clusterParam.getLujbean());
  }

  private NetRootActor networkActor(JambeanInLujcluster clusterParam,
      Map<String, GameProtoHandler<?>> handlerMap,
      Map<String, GameplayDataActor.CommandKit> cmdMap) {
    return new NetRootActor(new HashMap<>(), clusterParam.getNetAcceptHandler(),
        clusterParam.getNetDisconnectHandler(), handlerMap, cmdMap,
        clusterParam.getLujnet(), clusterParam.getNetReceivePlugin(), clusterParam.getNetParam(),
        clusterParam.getLujbean());
  }

  private DynamicRootActor dynamicActor(JambeanInLujcluster clusterParam) {
    return DynamicRootActor.create(clusterParam);
  }
}
