package luj.game.server.internal.network.receive.frame;

import io.netty.buffer.ByteBuf;
import luj.game.server.api.plugin.JamverNetReceiveFrame;

final class ContextImpl implements JamverNetReceiveFrame.Context {

  @Override
  public ByteBuf getLastFrame() {
    return _lastFrame;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getConnectionState() {
    return (T) _connState;
  }

  @Override
  public JamverNetReceiveFrame.Result then() {
    return _result;
  }

  ByteBuf _lastFrame;
  Object _connState;

  ResultImpl _result;
}
