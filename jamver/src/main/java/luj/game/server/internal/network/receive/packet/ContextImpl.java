package luj.game.server.internal.network.receive.packet;

import luj.game.server.api.plugin.JamverNetReceivePacket;

final class ContextImpl implements JamverNetReceivePacket.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <P> P getPacket(JamverNetReceivePacket<P> plugin) {
    return (P) _packet;
  }

  Object _packet;
}
