package luj.game.server.internal.luj.lujcluster.actor.network;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.boot.plugin.BootStartInvoker;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
import luj.net.api.NetContext;

public class NetRootActor {

  public interface Handler<M> extends ActorMessageHandler<NetRootActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<NetRootActor> {
    // NOOP
  }

  public NetRootActor(Map<Class<?>, GameProtoHandler<?>> handlerMap,
      Map<Class<?>, GameplayDataActor.CommandKit> commandMap, NetContext lujnet,
      NetReceivePlugin receivePlugin, BootStartInvoker.Network netParam, BeanContext lujbean) {
    _handlerMap = handlerMap;
    _commandMap = commandMap;
    _lujnet = lujnet;
    _receivePlugin = receivePlugin;
    _netParam = netParam;
    _lujbean = lujbean;
  }

  public TopLevelRefs getSiblingRef() {
    return _siblingRef;
  }

  public void setSiblingRef(TopLevelRefs siblingRef) {
    _siblingRef = siblingRef;
  }

  public Map<Class<?>, GameProtoHandler<?>> getHandlerMap() {
    return _handlerMap;
  }

  public Map<Class<?>, GameplayDataActor.CommandKit> getCommandMap() {
    return _commandMap;
  }

  public NetReceivePlugin getReceivePlugin() {
    return _receivePlugin;
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

  private final Map<Class<?>, GameProtoHandler<?>> _handlerMap;
  private final Map<Class<?>, GameplayDataActor.CommandKit> _commandMap;

  private final NetReceivePlugin _receivePlugin;
  private final BootStartInvoker.Network _netParam;

  private final NetContext _lujnet;
  private final BeanContext _lujbean;
}
