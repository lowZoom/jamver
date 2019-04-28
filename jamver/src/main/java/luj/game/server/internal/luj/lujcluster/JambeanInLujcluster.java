package luj.game.server.internal.luj.lujcluster;

import java.util.List;
import luj.game.server.api.event.GameEventListener;

public class JambeanInLujcluster {

  public JambeanInLujcluster(List<GameEventListener<?>> eventListenerList) {
    _eventListenerList = eventListenerList;
  }

  public List<GameEventListener<?>> getEventListenerList() {
    return _eventListenerList;
  }

  private final List<GameEventListener<?>> _eventListenerList;
}
