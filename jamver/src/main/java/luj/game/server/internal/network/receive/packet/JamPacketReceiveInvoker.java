package luj.game.server.internal.network.receive.packet;

import luj.game.server.api.plugin.JamverNetReceivePacket;

public enum JamPacketReceiveInvoker {
  GET;

  public void invoke(JamverNetReceivePacket<?> plugin, Object packet) {
    ContextImpl ctx = new ContextImpl();
    ctx._packet = packet;

    plugin.onReceive(ctx);
  }
}
