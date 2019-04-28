package luj.game.server.internal.luj.lujcluster.actor.gameplay.event;

import java.util.List;
import java.util.Map;
import luj.game.server.api.event.GameEventListener;

public class GameplayEventActor {

  public GameplayEventActor(Map<Class<?>, List<GameEventListener<?>>> listenerMap) {
    _listenerMap = listenerMap;
  }

  public Map<Class<?>, List<GameEventListener<?>>> getListenerMap() {
    return _listenerMap;
  }

  private final Map<Class<?>, List<GameEventListener<?>>> _listenerMap;
}
