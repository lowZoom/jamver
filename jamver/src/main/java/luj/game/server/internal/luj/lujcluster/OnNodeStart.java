package luj.game.server.internal.luj.lujcluster;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.cluster.api.node.NodeStartListener;
import luj.config.api.container.ConfigContainer;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerHealthListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.cluster.message.handle.collect.ClusterHandleMapCollector;
import luj.game.server.internal.config.reload.ConfigReloadInvoker;
import luj.game.server.internal.data.command.collect.CommandMapCollector;
import luj.game.server.internal.data.command.collect.group.GroupMapCollector;
import luj.game.server.internal.dynamic.init.DynamicInitInvoker;
import luj.game.server.internal.event.listener.collect.EventListenerMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.DataActorFactory;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.GameplayEventActor;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.luj.lujcluster.actor.start.JamStartActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.StartRefMsg;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
import luj.game.server.internal.network.proto.handle.collect.ProtoHandleCollector;
import luj.spring.anno.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnNodeStart implements NodeStartListener {

  /**
   * @see luj.game.server.internal.boot.node.ServerInstanceStarter#start
   * @see luj.game.server.internal.luj.lujcluster.actor.start.OnPrestart#onHandle
   */
  @Override
  public void onStart(Context ctx) throws Exception {
    JambeanInLujcluster param = ctx.getStartParam();

    //TODO: 考虑下不要并发使用，同时支持热更（改成不可变再走消息群发同步？）
    Map<String, GameplayDataActor.CommandKit> cmdMap = new ConcurrentHashMap<>(
        new CommandMapCollector(param.getDataCommandList(), param.getDataLoadList()).collect());

    ProtoHandleCollector.Result handleCollect =
        new ProtoHandleCollector(param.getProtoHandlerList()).collect();

    // 先创建所有actor
    var allRef = new TopLevelRefs(
        ctx.createApplicationActor(dataActor(param, cmdMap)),
        ctx.createApplicationActor(eventActor(param, cmdMap)),
        ctx.createApplicationActor(clusterActor(param, cmdMap)),
        ctx.createApplicationActor(networkActor(param, handleCollect, cmdMap))
    );

    // 初始化对外接口
    param.getJamverInternal().network0().init(allRef.getNetworkRef());

    // 初始化热更加载
    Collection<GameStartListener> dynamicStart = initDynamic(param, allRef);

    // 最后等待各actor初始化完成
    List<Tellable> refList = ImmutableList.of(allRef.getDataRef(),
        allRef.getEventRef(), allRef.getClusterRef(), allRef.getNetworkRef());

    var startLatch = new CountDownLatch(refList.size());
    var msg = new StartRefMsg(allRef, startLatch);
    for (Tellable ref : refList) {
      ref.tell(msg);
    }

    startLatch.await();

    // 初始化完成后，再调用外部启动监听
    JamStartActor startState = JamStartActor.create(dynamicStart, param, allRef, cmdMap);
    ctx.createApplicationActor(startState);
  }

  private Collection<GameStartListener> initDynamic(JambeanInLujcluster clusterParam,
      TopLevelRefs allRef) {
    JamverDynamicRootInit initPlugin = clusterParam.getAllPlugin().getDynamicInit();
    if (initPlugin == null) {
      LOG.debug("[game]未启用热更模块");
      return ImmutableList.of();
    }

    Object appParam = clusterParam.getAppStartParam();
    return new DynamicInitInvoker(initPlugin, allRef, appParam).invoke();
  }

  private GameplayDataActor dataActor(JambeanInLujcluster clusterParam,
      Map<String, GameplayDataActor.CommandKit> cmdMap) {
    CacheSession lujcache = clusterParam.getLujcache();
    CacheContainer dataCache = lujcache.createCache();

    JamPluginCollect plugin = clusterParam.getAllPlugin();
    ConfigContainer configs = new ConfigReloadInvoker(
        plugin.getConfigReload(), clusterParam.getAppStartParam()).invoke();

    Map<Class<?>, GameplayDataActor.GroupKit> groupMap =
        new GroupMapCollector(clusterParam.getCommandGroupList()).collect();

    return new DataActorFactory(clusterParam, dataCache, configs, cmdMap, groupMap).create();
  }

  private GameplayEventActor eventActor(JambeanInLujcluster clusterParam,
      Map<String, GameplayDataActor.CommandKit> cmdMap) {
    Map<String, List<GameEventListener<?>>> listenerMap = new EventListenerMapCollector(
        clusterParam.getEventListenerList()).collect();

    return new GameplayEventActor(listenerMap, cmdMap, clusterParam.getLujbean());
  }

  private ClusterCommActor clusterActor(JambeanInLujcluster clusterParam,
      Map<String, GameplayDataActor.CommandKit> cmdMap) {
    Map<String, ServerMessageHandler<?>> handlerMap =
        new ClusterHandleMapCollector(clusterParam.getClusterMsgHandleList()).collect();

    List<ServerJoinListener> joinList = new ArrayList<>(clusterParam.getClusterJoinList());
    List<ServerHealthListener> healthList = new ArrayList<>(clusterParam.getClusterHealthList());

    JamPluginCollect plugin = clusterParam.getAllPlugin();
    return new ClusterCommActor(joinList, healthList, handlerMap,
        plugin.getClusterProto(), cmdMap, clusterParam.getLujbean());
  }

  private NetRootActor networkActor(JambeanInLujcluster clusterParam,
      ProtoHandleCollector.Result handler, Map<String, GameplayDataActor.CommandKit> cmdMap) {
    JamPluginCollect plugin = clusterParam.getAllPlugin();

    return new NetRootActor(handler.handleMap(), new ArrayList<>(handler.defaultHandler()),
        cmdMap, plugin.getNetAll(), clusterParam.getLujbean());
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnNodeStart.class);
}
