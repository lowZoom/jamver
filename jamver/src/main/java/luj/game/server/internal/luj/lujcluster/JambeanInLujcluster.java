package luj.game.server.internal.luj.lujcluster;

import java.util.List;
import luj.game.server.api.event.GameEventListener;

public class JambeanInLujcluster {

  public JambeanInLujcluster(List<GameEventListener<?>> eventListenerList,
      GameEventListener.Service eventListenService) {
    _eventListenerList = eventListenerList;
    _eventListenService = eventListenService;
  }

  public List<GameEventListener<?>> getEventListenerList() {
    return _eventListenerList;
  }

  public GameEventListener.Service getEventListenService() {
    return _eventListenService;
  }

  private final List<GameEventListener<?>> _eventListenerList;

  private final GameEventListener.Service _eventListenService;
}
