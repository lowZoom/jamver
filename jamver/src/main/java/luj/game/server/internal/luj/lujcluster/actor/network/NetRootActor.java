package luj.game.server.internal.luj.lujcluster.actor.network;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

/**
 * 网络模块
 */
public class NetRootActor {

  public interface Handler<M> extends ActorMessageHandler<NetRootActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<NetRootActor> {
    // NOOP
  }

  public NetRootActor(Map<String, GameProtoHandler<?>> protoHandlerMap,
      Map<String, GameplayDataActor.CommandKit> commandMap, NetAllPlugin allPlugin,
      BeanContext lujbean) {
    _protoHandlerMap = protoHandlerMap;
    _commandMap = commandMap;
    _allPlugin = allPlugin;
    _lujbean = lujbean;
  }

  public TopLevelRefs getSiblingRef() {
    return _siblingRef;
  }

  public void setSiblingRef(TopLevelRefs siblingRef) {
    _siblingRef = siblingRef;
  }

  public Map<String, GameProtoHandler<?>> getProtoHandlerMap() {
    return _protoHandlerMap;
  }

  public Map<String, GameplayDataActor.CommandKit> getCommandMap() {
    return _commandMap;
  }

  public NetAllPlugin getAllPlugin() {
    return _allPlugin;
  }

  public BeanContext getLujbean() {
    return _lujbean;
  }

  private TopLevelRefs _siblingRef;

  private final Map<String, GameProtoHandler<?>> _protoHandlerMap;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final NetAllPlugin _allPlugin;
  private final BeanContext _lujbean;
}
