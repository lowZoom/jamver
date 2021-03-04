package luj.game.server.internal.luj.lujcluster.actor.cluster;

import com.google.common.collect.Multimap;
import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

public class ClusterCommActor {

  public interface Handler<M> extends ActorMessageHandler<ClusterCommActor, M> {
    // NOOP
  }

  public ClusterCommActor(Multimap<String, ActorMessageHandler.Node> dispatchMap,
      Map<String, ServerMessageHandler<?>> handlerMap,
      Map<Class<?>, GameplayDataActor.CommandKit> commandMap, ClusterProtoPlugin protoPlugin,
      BeanContext lujbean) {
    _dispatchMap = dispatchMap;
    _handlerMap = handlerMap;
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

  public Map<Class<?>, GameplayDataActor.CommandKit> getCommandMap() {
    return _commandMap;
  }

  public ClusterProtoPlugin getProtoPlugin() {
    return _protoPlugin;
  }

  public BeanContext getLujbean() {
    return _lujbean;
  }

  private TopLevelRefs _siblingRef;
  private final Multimap<String, ActorMessageHandler.Node> _dispatchMap;

  //////////////

  private final Map<String, ServerMessageHandler<?>> _handlerMap;
  private final Map<Class<?>, GameplayDataActor.CommandKit> _commandMap;

  private final ClusterProtoPlugin _protoPlugin;
  private final BeanContext _lujbean;
}
