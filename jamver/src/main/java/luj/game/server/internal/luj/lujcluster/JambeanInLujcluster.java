package luj.game.server.internal.luj.lujcluster;

import java.util.List;
import luj.cache.api.CacheSession;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.api.event.GameEventListener.Service;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.DataAllPlugin;

public class JambeanInLujcluster {

  public JambeanInLujcluster(
      List<GameStartListener> startListenerList,
      List<GameDataCommand<?, ?>> dataCommandList,
      List<GameDataLoad<?, ?>> dataLoadList,
      List<GameEventListener<?>> eventListenerList,
      Service eventListenService,
      List<ServerMessageHandler<?>> clusterMessageList,
      List<ServerJoinListener> clusterJoinList,
      DataAllPlugin dataAllPlugin,
      CacheSession lujcache, Object appStartParam) {
    _startListenerList = startListenerList;
    _dataCommandList = dataCommandList;
    _dataLoadList = dataLoadList;
    _eventListenerList = eventListenerList;
    _eventListenService = eventListenService;
    _clusterMessageList = clusterMessageList;
    _clusterJoinList = clusterJoinList;
    _dataAllPlugin = dataAllPlugin;
    _lujcache = lujcache;
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

  public List<GameEventListener<?>> getEventListenerList() {
    return _eventListenerList;
  }

  public GameEventListener.Service getEventListenService() {
    return _eventListenService;
  }

  public List<ServerMessageHandler<?>> getClusterMessageList() {
    return _clusterMessageList;
  }

  public List<ServerJoinListener> getClusterJoinList() {
    return _clusterJoinList;
  }

  public DataAllPlugin getDataAllPlugin() {
    return _dataAllPlugin;
  }

  public CacheSession getLujcache() {
    return _lujcache;
  }

  public Object getAppStartParam() {
    return _appStartParam;
  }

  private final List<GameStartListener> _startListenerList;

  private final List<GameDataCommand<?, ?>> _dataCommandList;
  private final List<GameDataLoad<?, ?>> _dataLoadList;

  private final List<GameEventListener<?>> _eventListenerList;
  private final GameEventListener.Service _eventListenService;

  private final List<ServerMessageHandler<?>> _clusterMessageList;
  private final List<ServerJoinListener> _clusterJoinList;

  private final DataAllPlugin _dataAllPlugin;

  private final CacheSession _lujcache;

  /**
   * @see luj.game.server.api.plugin.JamverBootRootInit.Return#param
   */
  private final Object _appStartParam;
}
