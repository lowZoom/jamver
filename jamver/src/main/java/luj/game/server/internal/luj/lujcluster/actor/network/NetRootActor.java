package luj.game.server.internal.luj.lujcluster.actor.network;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.net.GameAcceptHandler;
import luj.game.server.api.net.GameDisconnectHandler;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.boot.plugin.BootStartInvoker;
import luj.game.server.internal.boot.plugin.BootStartInvoker.Network;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor.CommandKit;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
import luj.net.api.NetContext;
import luj.net.api.server.ConnectionAcceptInitializer;
import luj.net.api.server.ConnectionAcceptInitializer.Connection;

public class NetRootActor {

  public interface Handler<M> extends ActorMessageHandler<NetRootActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<NetRootActor> {
    // NOOP
  }

  public NetRootActor(Map<Integer, Connection> connectionMap,
      GameAcceptHandler acceptHandler,
      GameDisconnectHandler disconnectHandler,
      Map<Class<?>, GameProtoHandler<?>> protoHandlerMap,
      Map<Class<?>, CommandKit> commandMap, NetContext lujnet,
      NetAllPlugin allPlugin, Network netParam, BeanContext lujbean) {
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

  public Map<Class<?>, GameProtoHandler<?>> getProtoHandlerMap() {
    return _protoHandlerMap;
  }

  public Map<Class<?>, GameplayDataActor.CommandKit> getCommandMap() {
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

  private final Map<Class<?>, GameProtoHandler<?>> _protoHandlerMap;
  private final Map<Class<?>, GameplayDataActor.CommandKit> _commandMap;

  private final NetAllPlugin _allPlugin;
  private final BootStartInvoker.Network _netParam;

  private final NetContext _lujnet;
  private final BeanContext _lujbean;
}
