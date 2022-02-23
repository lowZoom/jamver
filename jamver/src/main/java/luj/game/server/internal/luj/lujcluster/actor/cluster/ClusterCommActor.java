package luj.game.server.internal.luj.lujcluster.actor.cluster;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

public class ClusterCommActor {

  public interface Handler<M> extends ActorMessageHandler<ClusterCommActor, M> {
    // NOOP
  }

  public ClusterCommActor(List<ServerJoinListener> joinListeners,
      Map<String, ServerMessageHandler<?>> handlerMap, ClusterProtoPlugin protoPlugin,
      Map<String, GameplayDataActor.CommandKit> commandMap, BeanContext lujbean) {
    _handlerMap = handlerMap;
    _joinListeners = joinListeners;
    _commandMap = commandMap;
    _protoPlugin = protoPlugin;
    _lujbean = lujbean;
  }

  public TopLevelRefs getSiblingRef() {
    return _siblingRef;
  }

  public void setSiblingRef(TopLevelRefs siblingRef) {
    _siblingRef = siblingRef;
  }

  public Multimap<String, ActorMessageHandler.Node> getDispatchMap() {
    return _dispatchMap;
  }

  public Map<String, ServerMessageHandler<?>> getHandlerMap() {
    return _handlerMap;
  }

  public List<ServerJoinListener> getJoinListeners() {
    return _joinListeners;
  }

  public Map<String, GameplayDataActor.CommandKit> getCommandMap() {
    return _commandMap;
  }

  public ClusterProtoPlugin getProtoPlugin() {
    return _protoPlugin;
  }

  public BeanContext getLujbean() {
    return _lujbean;
  }

  private TopLevelRefs _siblingRef;

  private final Multimap<String, ActorMessageHandler.Node> _dispatchMap
      = ArrayListMultimap.create();

  //////////////

  private final List<ServerJoinListener> _joinListeners;

  private final Map<String, ServerMessageHandler<?>> _handlerMap;
  private final ClusterProtoPlugin _protoPlugin;

  private final Map<String, GameplayDataActor.CommandKit> _commandMap;
  private final BeanContext _lujbean;
}
