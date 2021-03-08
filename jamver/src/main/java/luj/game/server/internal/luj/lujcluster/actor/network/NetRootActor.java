package luj.game.server.internal.luj.lujcluster.actor.network;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.api.net.NetAcceptHandler;
import luj.game.server.internal.boot.plugin.BootStartInvoker;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
import luj.net.api.NetContext;
import luj.net.api.server.ConnectionAcceptInitializer;

public class NetRootActor {

  public interface Handler<M> extends ActorMessageHandler<NetRootActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<NetRootActor> {
    // NOOP
  }

  public NetRootActor(Map<Integer, ConnectionAcceptInitializer.Connection> connectionMap,
      NetAcceptHandler acceptHandler, Map<Class<?>, GameProtoHandler<?>> protoHandlerMap,
      Map<Class<?>, GameplayDataActor.CommandKit> commandMap, NetContext lujnet,
      NetAllPlugin allPlugin, BootStartInvoker.Network netParam, BeanContext lujbean) {
    _connectionMap = connectionMap;
    _acceptHandler = acceptHandler;
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

  public NetAcceptHandler getAcceptHandler() {
    return _acceptHandler;
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

  private final NetAcceptHandler _acceptHandler;
  private final Map<Class<?>, GameProtoHandler<?>> _protoHandlerMap;
  private final Map<Class<?>, GameplayDataActor.CommandKit> _commandMap;

  private final NetAllPlugin _allPlugin;
  private final BootStartInvoker.Network _netParam;

  private final NetContext _lujnet;
  private final BeanContext _lujbean;
}
