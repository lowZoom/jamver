package luj.game.server.internal.luj.lujcluster.actor.network;

import java.util.List;
import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
import luj.game.server.internal.network.proto.handle.collect.ProtoHandleMap;

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

  public NetRootActor(ProtoHandleMap protoHandleMap, List<GameProtoHandler.Default> defaultHandler,
      Map<String, GameplayDataActor.CommandKit> commandMap, NetAllPlugin allPlugin,
      BeanContext lujbean) {
    _protoHandleMap = protoHandleMap;
    _defaultHandler = defaultHandler;
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

  public ProtoHandleMap getProtoHandleMap() {
    return _protoHandleMap;
  }

  public List<GameProtoHandler.Default> getDefaultHandler() {
    return _defaultHandler;
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

  private final ProtoHandleMap _protoHandleMap;
  private final List<GameProtoHandler.Default> _defaultHandler;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final NetAllPlugin _allPlugin;
  private final BeanContext _lujbean;
}
