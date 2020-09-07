package luj.game.server.internal.luj.lujcluster.actor.gameplay.event;

import java.util.List;
import java.util.Map;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopRefCollection;

public class GameplayEventActor {

  public interface Handler<M> extends ActorMessageHandler<GameplayEventActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<GameplayEventActor> {
    // NOOP
  }

  public GameplayEventActor(Map<Class<?>, List<GameEventListener<?>>> listenerMap,
      GameEventListener.Service listenService) {
    _listenerMap = listenerMap;
    _listenService = listenService;
  }

  public TopRefCollection getSiblingRef() {
    return _siblingRef;
  }

  public void setSiblingRef(TopRefCollection siblingRef) {
    _siblingRef = siblingRef;
  }

  public Map<Class<?>, List<GameEventListener<?>>> getListenerMap() {
    return _listenerMap;
  }

  public GameEventListener.Service getListenService() {
    return _listenService;
  }

  private TopRefCollection _siblingRef;

  private final Map<Class<?>, List<GameEventListener<?>>> _listenerMap;
  private final GameEventListener.Service _listenService;
}
