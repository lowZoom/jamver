package luj.game.server.internal.luj.lujnet;

import luj.game.server.api.plugin.JamverNetReceiveFrame;

public class NetConnState {

  public NetConnState(JambeanInLujnet bindParam) {
    _bindParam = bindParam;
  }

  public JamverNetReceiveFrame getNextReceiver() {
    return _nextReceiver;
  }

  public void setNextReceiver(JamverNetReceiveFrame nextReceiver) {
    _nextReceiver = nextReceiver;
  }

  public JambeanInLujnet getBindParam() {
    return _bindParam;
  }

  private JamverNetReceiveFrame _nextReceiver;

  private final JambeanInLujnet _bindParam;
}
