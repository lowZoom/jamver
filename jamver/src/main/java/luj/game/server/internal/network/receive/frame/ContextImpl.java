package luj.game.server.internal.network.receive.frame;

import luj.game.server.api.plugin.JamverNetReceiveFrame;

final class ContextImpl implements JamverNetReceiveFrame.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getLastFrame() {
    return (T) _lastFrame;
  }

  @Override
  public JamverNetReceiveFrame.Result then() {
    return _result;
  }

  Object _lastFrame;

  ResultImpl _result;
}
