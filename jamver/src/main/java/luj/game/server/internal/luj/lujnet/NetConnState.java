package luj.game.server.internal.luj.lujnet;

import luj.game.server.api.plugin.JamverNetReceiveFrame;

public class NetConnState {

  public NetConnState(Integer connectionId, Object pluginState, JambeanInLujnet bindParam) {
    _connectionId = connectionId;
    _pluginState = pluginState;
    _bindParam = bindParam;
  }

  public JamverNetReceiveFrame getNextReceiver() {
    return _nextReceiver;
  }

  public Integer getConnectionId() {
    return _connectionId;
  }

  public Object getPluginState() {
    return _pluginState;
  }

  public void setNextReceiver(JamverNetReceiveFrame nextReceiver) {
    _nextReceiver = nextReceiver;
  }

  public JambeanInLujnet getBindParam() {
    return _bindParam;
  }

  private JamverNetReceiveFrame _nextReceiver;

  private final Integer _connectionId;
  private final Object _pluginState;

  private final JambeanInLujnet _bindParam;
}
