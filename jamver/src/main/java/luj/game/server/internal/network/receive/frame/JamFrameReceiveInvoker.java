package luj.game.server.internal.network.receive.frame;

import luj.game.server.api.plugin.JamverNetReceiveFrame;

public enum JamFrameReceiveInvoker {
  GET;

  public interface Result {

    int waitByteCount();

    JamverNetReceiveFrame nextReceiver();

    Object resultPacket();
  }

  public Result invoke(JamverNetReceiveFrame framePlugin, Object lastFrame) throws Exception {
    ContextImpl ctx = new ContextImpl();

    ResultImpl result = new ResultImpl();
    ctx._lastFrame = lastFrame;
    ctx._result = result;

    framePlugin.receive(ctx);
    return result;
  }
}
