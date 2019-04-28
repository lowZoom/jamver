package luj.game.server.internal.luj.lujcluster.actor.gameplay.event;

import java.util.List;
import java.util.Map;
import luj.game.server.api.event.GameEventListener;

public class GameplayEventActor {

  public GameplayEventActor(Map<Class<?>, List<GameEventListener<?>>> listenerMap,
      GameEventListener.Service listenService) {
    _listenerMap = listenerMap;
    _listenService = listenService;
  }

  public Map<Class<?>, List<GameEventListener<?>>> getListenerMap() {
    return _listenerMap;
  }

  public GameEventListener.Service getListenService() {
    return _listenService;
  }

  private final Map<Class<?>, List<GameEventListener<?>>> _listenerMap;

  private final GameEventListener.Service _listenService;
}
