package luj.game.server.internal.luj.lujcluster;

import java.util.List;
import luj.bean.api.BeanContext;
import luj.cache.api.CacheSession;
import luj.config.api.ConfigSession;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerHealthListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.api.net.GameProtoHandler;

public class JambeanInLujcluster {

  public JambeanInLujcluster(List<GameStartListener> startListenerList,
      List<GameDataCommand<?, ?>> dataCommandList, List<GameDataLoad<?, ?>> dataLoadList,
      List<GameDataCommandGroup> commandGroupList,
      List<ServerMessageHandler<?>> clusterMsgHandleList,
      List<ServerJoinListener> clusterJoinList, List<ServerHealthListener> clusterHealthList,
      List<GameEventListener<?>> eventListenerList, List<GameProtoHandler<?>> protoHandlerList,
      JamPluginCollect allPlugin, CacheSession lujcache,
      ConfigSession lujconfig, BeanContext lujbean,
      Object appStartParam, Object appShutdownParam) {
    _startListenerList = startListenerList;
    _dataCommandList = dataCommandList;
    _dataLoadList = dataLoadList;
    _commandGroupList = commandGroupList;
    _clusterMsgHandleList = clusterMsgHandleList;
    _clusterJoinList = clusterJoinList;
    _clusterHealthList = clusterHealthList;
    _eventListenerList = eventListenerList;
    _protoHandlerList = protoHandlerList;
    _allPlugin = allPlugin;
    _lujcache = lujcache;
    _lujconfig = lujconfig;
    _lujbean = lujbean;
    _appStartParam = appStartParam;
    _appShutdownParam = appShutdownParam;
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

  public List<ServerMessageHandler<?>> getClusterMsgHandleList() {
    return _clusterMsgHandleList;
  }

  public List<ServerJoinListener> getClusterJoinList() {
    return _clusterJoinList;
  }

  public List<ServerHealthListener> getClusterHealthList() {
    return _clusterHealthList;
  }

  public List<GameProtoHandler<?>> getProtoHandlerList() {
    return _protoHandlerList;
  }

  public JamPluginCollect getAllPlugin() {
    return _allPlugin;
  }

  public CacheSession getLujcache() {
    return _lujcache;
  }

  public ConfigSession getLujconfig() {
    return _lujconfig;
  }

  public BeanContext getLujbean() {
    return _lujbean;
  }

  public Object getAppStartParam() {
    return _appStartParam;
  }

  public Object getAppShutdownParam() {
    return _appShutdownParam;
  }

  private final List<GameStartListener> _startListenerList;

  private final List<GameDataCommand<?, ?>> _dataCommandList;
  private final List<GameDataLoad<?, ?>> _dataLoadList;
  private final List<GameDataCommandGroup> _commandGroupList;

  private final List<ServerMessageHandler<?>> _clusterMsgHandleList;
  private final List<ServerJoinListener> _clusterJoinList;
  private final List<ServerHealthListener> _clusterHealthList;

  private final List<GameEventListener<?>> _eventListenerList;
  private final List<GameProtoHandler<?>> _protoHandlerList;

  private final JamPluginCollect _allPlugin;

  private final CacheSession _lujcache;
  private final ConfigSession _lujconfig;
  private final BeanContext _lujbean;

  /**
   * @see luj.game.server.api.plugin.JamverBootRootInit.Param#start
   */
  private final Object _appStartParam;
  private final Object _appShutdownParam;
}
