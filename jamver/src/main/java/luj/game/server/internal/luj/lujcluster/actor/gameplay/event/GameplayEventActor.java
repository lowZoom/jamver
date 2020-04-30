package luj.game.server.internal.luj.lujcluster.actor.gameplay.event;

import java.util.List;
import java.util.Map;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.event.GameEventListener;

public class GameplayEventActor {

  public interface Handler<M> extends ActorMessageHandler<GameplayEventActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<GameplayEventActor> {
    // NOOP
  }

  public GameplayEventActor(Map<Class<?>, List<GameEventListener<?>>> listenerMap,
      GameEventListener.Service listenService, NodeStartListener.Actor dataRef) {
    _listenerMap = listenerMap;
    _listenService = listenService;
    _dataRef = dataRef;
  }

  public Map<Class<?>, List<GameEventListener<?>>> getListenerMap() {
    return _listenerMap;
  }

  public GameEventListener.Service getListenService() {
    return _listenService;
  }

  private final Map<Class<?>, List<GameEventListener<?>>> _listenerMap;
  private final GameEventListener.Service _listenService;

  private final NodeStartListener.Actor _dataRef;
}
