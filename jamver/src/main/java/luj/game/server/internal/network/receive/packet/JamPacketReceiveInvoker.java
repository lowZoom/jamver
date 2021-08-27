package luj.game.server.internal.network.receive.packet;

import luj.game.server.api.plugin.JamverNetReceivePacket;

public enum JamPacketReceiveInvoker {
  GET;

  public interface Result {

    String protoType();

    Object protoInstance();
  }

  public Result invoke(JamverNetReceivePacket<?> plugin, Object packet) {
    ContextImpl ctx = new ContextImpl();
    ctx._packet = packet;

    ResultImpl result = new ResultImpl();
    ctx._result = result;

    plugin.receive(ctx);
    return result;
  }
}
