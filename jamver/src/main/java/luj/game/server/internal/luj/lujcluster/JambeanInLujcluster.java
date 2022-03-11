package luj.game.server.internal.luj.lujcluster;

import java.util.List;
import luj.bean.api.BeanContext;
import luj.cache.api.CacheSession;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerHealthListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.api.net.GameAcceptHandler;
import luj.game.server.api.net.GameDisconnectHandler;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.boot.plugin.BootStartInvoker;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterProtoPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.DataAllPlugin;
import luj.game.server.internal.luj.lujcluster.actor.network.NetAllPlugin;
import luj.net.api.NetContext;

public class JambeanInLujcluster {

  public JambeanInLujcluster(List<GameStartListener> startListenerList,
      List<GameDataCommand<?, ?>> dataCommandList, List<GameDataLoad<?, ?>> dataLoadList,
      List<GameDataCommandGroup> commandGroupList,
      List<GameEventListener<?>> eventListenerList, GameEventListener.Service eventListenService,
      List<ServerMessageHandler<?>> clusterMsgHandleList,
      List<ServerJoinListener> clusterJoinList, List<ServerHealthListener> clusterHealthList,
      GameAcceptHandler netAcceptHandler, GameDisconnectHandler netDisconnectHandler,
      List<GameProtoHandler<?>> protoHandlerList,
      DataAllPlugin dataAllPlugin,
      ClusterProtoPlugin clusterProtoPlugin,
      NetAllPlugin netReceivePlugin, JamverDynamicRootInit dynamicInitPlugin,
      CacheSession lujcache, BeanContext lujbean,
      NetContext lujnet, BootStartInvoker.Network netParam,
      Object appStartParam) {
    _startListenerList = startListenerList;
    _dataCommandList = dataCommandList;
    _dataLoadList = dataLoadList;
    _commandGroupList = commandGroupList;
    _eventListenerList = eventListenerList;
    _eventListenService = eventListenService;
    _clusterMsgHandleList = clusterMsgHandleList;
    _clusterJoinList = clusterJoinList;
    _clusterHealthList = clusterHealthList;
    _netAcceptHandler = netAcceptHandler;
    _netDisconnectHandler = netDisconnectHandler;
    _protoHandlerList = protoHandlerList;
    _dataAllPlugin = dataAllPlugin;
    _clusterProtoPlugin = clusterProtoPlugin;
    _netReceivePlugin = netReceivePlugin;
    _dynamicInitPlugin = dynamicInitPlugin;
    _lujcache = lujcache;
    _lujbean = lujbean;
    _lujnet = lujnet;
    _netParam = netParam;
    _appStartParam = appStartParam;
  }

  public List<GameStartListener> getStartListenerList() {
    return _startListenerList;
  }

  public List<GameDataCommand<?, ?>> getDataCommandList() {
    return _dataCommandList;
  }

  public List<GameDataLoad<?, ?>> getDataLoadList() {
    return _dataLoadList;
  }

  public List<GameDataCommandGroup> getCommandGroupList() {
    return _commandGroupList;
  }

  public List<GameEventListener<?>> getEventListenerList() {
    return _eventListenerList;
  }

  public GameEventListener.Service getEventListenService() {
    return _eventListenService;
  }

  public List<ServerMessageHandler<?>> getClusterMsgHandleList() {
    return _clusterMsgHandleList;
  }

  public List<ServerJoinListener> getClusterJoinList() {
    return _clusterJoinList;
  }

  public List<ServerHealthListener> getClusterHealthList() {
    return _clusterHealthList;
  }

  public DataAllPlugin getDataAllPlugin() {
    return _dataAllPlugin;
  }

  public ClusterProtoPlugin getClusterProtoPlugin() {
    return _clusterProtoPlugin;
  }

  public GameAcceptHandler getNetAcceptHandler() {
    return _netAcceptHandler;
  }

  public GameDisconnectHandler getNetDisconnectHandler() {
    return _netDisconnectHandler;
  }

  public List<GameProtoHandler<?>> getProtoHandlerList() {
    return _protoHandlerList;
  }

  public NetAllPlugin getNetReceivePlugin() {
    return _netReceivePlugin;
  }

  public JamverDynamicRootInit getDynamicInitPlugin() {
    return _dynamicInitPlugin;
  }

  public CacheSession getLujcache() {
    return _lujcache;
  }

  public BeanContext getLujbean() {
    return _lujbean;
  }

  public NetContext getLujnet() {
    return _lujnet;
  }

  public BootStartInvoker.Network getNetParam() {
    return _netParam;
  }

  public Object getAppStartParam() {
    return _appStartParam;
  }

  private final List<GameStartListener> _startListenerList;

  private final List<GameDataCommand<?, ?>> _dataCommandList;
  private final List<GameDataLoad<?, ?>> _dataLoadList;
  private final List<GameDataCommandGroup> _commandGroupList;

  private final List<GameEventListener<?>> _eventListenerList;
  private final GameEventListener.Service _eventListenService;

  private final List<ServerMessageHandler<?>> _clusterMsgHandleList;
  private final List<ServerJoinListener> _clusterJoinList;
  private final List<ServerHealthListener> _clusterHealthList;

  private final GameAcceptHandler _netAcceptHandler;
  private final GameDisconnectHandler _netDisconnectHandler;
  private final List<GameProtoHandler<?>> _protoHandlerList;

  private final DataAllPlugin _dataAllPlugin;
  private final ClusterProtoPlugin _clusterProtoPlugin;
  private final NetAllPlugin _netReceivePlugin;
  private final JamverDynamicRootInit _dynamicInitPlugin;

  private final CacheSession _lujcache;
  private final BeanContext _lujbean;

  private final NetContext _lujnet;
  private final BootStartInvoker.Network _netParam;

  /**
   * @see luj.game.server.api.plugin.JamverBootRootInit.Return#param
   */
  private final Object _appStartParam;
}
