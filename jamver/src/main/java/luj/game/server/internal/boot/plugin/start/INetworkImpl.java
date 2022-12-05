package luj.game.server.internal.boot.plugin.start;

import luj.cluster.api.actor.Tellable;
import luj.game.server.api.plugin.JamverBootRootInit;
import luj.game.server.internal.luj.lujcluster.actor.network.receive.ReceiveProtoMsg;

final class INetworkImpl implements JamverBootRootInit.Network, BootStartInvoker.Network {

  @Override
  public void init(Tellable networkRef) {
    _networkRef = networkRef;
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.network.receive.OnReceiveProto#onHandle
   */
  @Override
  public void receiveProto(String key, Object proto, Object param) {
    _networkRef.tell(new ReceiveProtoMsg(key, proto, param));
  }

  Tellable _networkRef;
}
