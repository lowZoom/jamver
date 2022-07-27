package luj.game.server.internal.luj.lujcluster.actor.gameplay.event;

import java.util.List;
import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

public class GameplayEventActor {

  public interface Handler<M> extends ActorMessageHandler<GameplayEventActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<GameplayEventActor> {
    // NOOP
  }

  public GameplayEventActor(Map<String, List<GameEventListener<?>>> listenerMap,
      Map<String, GameplayDataActor.CommandKit> commandMap, BeanContext lujbean,
      GameEventListener.Service listenService) {
    _listenerMap = listenerMap;
    _commandMap = commandMap;
    _lujbean = lujbean;
    _listenService = listenService;
  }

  public TopLevelRefs getSiblingRef() {
    return _siblingRef;
  }

  public void setSiblingRef(TopLevelRefs siblingRef) {
    _siblingRef = siblingRef;
  }

  public Map<String, List<GameEventListener<?>>> getListenerMap() {
    return _listenerMap;
  }

  public Map<String, GameplayDataActor.CommandKit> getCommandMap() {
    return _commandMap;
  }

  public BeanContext getLujbean() {
    return _lujbean;
  }

  /**
   * @deprecated 准备移除
   */
  @Deprecated
  public GameEventListener.Service getListenService() {
    return _listenService;
  }

  private TopLevelRefs _siblingRef;

  private final Map<String, List<GameEventListener<?>>> _listenerMap;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final BeanContext _lujbean;
  private final GameEventListener.Service _listenService;
}
