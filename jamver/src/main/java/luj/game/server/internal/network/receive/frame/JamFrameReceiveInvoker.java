package luj.game.server.internal.network.receive.frame;

import io.netty.buffer.ByteBuf;
import luj.game.server.api.plugin.JamverNetReceiveFrame;

public enum JamFrameReceiveInvoker {
  GET;

  public interface Result {

    int waitByteCount();

    JamverNetReceiveFrame nextReceiver();

    Object resultPacket();
  }

  public Result invoke(JamverNetReceiveFrame plugin,
      Object state, ByteBuf lastFrame) throws Exception {
    ContextImpl ctx = new ContextImpl();
    ctx._connState = state;
    ctx._lastFrame = lastFrame;

    ResultImpl result = new ResultImpl();
    ctx._result = result;

    plugin.receive(ctx);
    return result;
  }
}
