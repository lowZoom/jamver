package luj.game.server.internal.luj.lujcluster.actor.network;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.net.GameAcceptHandler;
import luj.game.server.api.net.GameDisconnectHandler;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.boot.plugin.start.BootStartInvoker;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
import luj.net.api.NetContext;
import luj.net.api.server.ConnectionAcceptInitializer;

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

  public NetRootActor(Map<Integer, ConnectionAcceptInitializer.Connection> connectionMap,
      GameAcceptHandler acceptHandler, GameDisconnectHandler disconnectHandler,
      Map<String, GameProtoHandler<?>> protoHandlerMap,
      Map<String, GameplayDataActor.CommandKit> commandMap, NetContext lujnet,
      NetAllPlugin allPlugin, BootStartInvoker.Network netParam, BeanContext lujbean) {
    _connectionMap = connectionMap;
    _acceptHandler = acceptHandler;
    _disconnectHandler = disconnectHandler;
    _protoHandlerMap = protoHandlerMap;
    _commandMap = commandMap;
    _lujnet = lujnet;
    _allPlugin = allPlugin;
    _netParam = netParam;
    _lujbean = lujbean;
  }

  public TopLevelRefs getSiblingRef() {
    return _siblingRef;
  }

  public void setSiblingRef(TopLevelRefs siblingRef) {
    _siblingRef = siblingRef;
  }

  public Map<Integer, ConnectionAcceptInitializer.Connection> getConnectionMap() {
    return _connectionMap;
  }

  public GameAcceptHandler getAcceptHandler() {
    return _acceptHandler;
  }

  public GameDisconnectHandler getDisconnectHandler() {
    return _disconnectHandler;
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

  public BootStartInvoker.Network getNetParam() {
    return _netParam;
  }

  public NetContext getLujnet() {
    return _lujnet;
  }

  public BeanContext getLujbean() {
    return _lujbean;
  }

  private TopLevelRefs _siblingRef;
  private final Map<Integer, ConnectionAcceptInitializer.Connection> _connectionMap;

  private final GameAcceptHandler _acceptHandler;
  private final GameDisconnectHandler _disconnectHandler;

  private final Map<String, GameProtoHandler<?>> _protoHandlerMap;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final NetAllPlugin _allPlugin;
  private final BootStartInvoker.Network _netParam;

  private final NetContext _lujnet;
  private final BeanContext _lujbean;
}
