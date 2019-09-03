package luj.game.server.internal.luj.lujcluster;

import java.util.List;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.event.GameEventListener;

public class JambeanInLujcluster {

  public JambeanInLujcluster(List<GameEventListener<?>> eventListenerList,
      GameEventListener.Service eventListenService,
      List<ServerMessageHandler<?>> clusterHandlerList) {
    _eventListenerList = eventListenerList;
    _eventListenService = eventListenService;
    _clusterHandlerList = clusterHandlerList;
  }

  public List<GameEventListener<?>> getEventListenerList() {
    return _eventListenerList;
  }

  public GameEventListener.Service getEventListenService() {
    return _eventListenService;
  }

  public List<ServerMessageHandler<?>> getClusterHandlerList() {
    return _clusterHandlerList;
  }

  private final List<GameEventListener<?>> _eventListenerList;
  private final GameEventListener.Service _eventListenService;

  private final List<ServerMessageHandler<?>> _clusterHandlerList;
}
