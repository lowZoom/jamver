package luj.game.server.internal.network.receive.frame;

import luj.game.server.api.plugin.JamverNetReceiveFrame;

final class ResultImpl implements JamverNetReceiveFrame.Result, JamFrameReceiveInvoker.Result {

  @Override
  public JamverNetReceiveFrame.Result waitBytes(int byteCount) {
    _waitByteCount = byteCount;
    return this;
  }

  @Override
  public JamverNetReceiveFrame.Result nextReceiver(JamverNetReceiveFrame receiver) {
    _nextReceiver = receiver;
    return this;
  }

  @Override
  public JamverNetReceiveFrame.Result completeRecv(Object packet) {
    _resultPacket = packet;
    return this;
  }

  //////////////////////////////////////

  @Override
  public int waitByteCount() {
    return _waitByteCount;
  }

  @Override
  public JamverNetReceiveFrame nextReceiver() {
    return _nextReceiver;
  }

  @Override
  public Object resultPacket() {
    return _resultPacket;
  }

  //////////////////////////////////////

  int _waitByteCount;
  JamverNetReceiveFrame _nextReceiver;

  Object _resultPacket;
}
