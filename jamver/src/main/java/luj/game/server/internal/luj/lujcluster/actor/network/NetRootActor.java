package luj.game.server.internal.luj.lujcluster.actor.network;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.boot.plugin.BootStartInvoker;
import luj.net.api.NetContext;

public class NetRootActor {

  public interface Handler<M> extends ActorMessageHandler<NetRootActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<NetRootActor> {
    // NOOP
  }

  public NetRootActor(NetContext lujnet, NetReceivePlugin receivePlugin,
      BootStartInvoker.Network netParam) {
    _lujnet = lujnet;
    _receivePlugin = receivePlugin;
    _netParam = netParam;
  }

  public NetContext getLujnet() {
    return _lujnet;
  }

  public NetReceivePlugin getReceivePlugin() {
    return _receivePlugin;
  }

  public BootStartInvoker.Network getNetParam() {
    return _netParam;
  }

  private final NetContext _lujnet;
  private final NetReceivePlugin _receivePlugin;

  private final BootStartInvoker.Network _netParam;
}
