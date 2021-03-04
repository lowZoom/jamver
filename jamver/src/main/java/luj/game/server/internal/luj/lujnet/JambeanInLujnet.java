package luj.game.server.internal.luj.lujnet;

import luj.cluster.api.actor.Tellable;
import luj.game.server.api.plugin.JamverNetReceiveFrame;

public class JambeanInLujnet {

  public JambeanInLujnet(JamverNetReceiveFrame framePlugin, Tellable netRef) {
    _framePlugin = framePlugin;
    _netRef = netRef;
  }

  public JamverNetReceiveFrame getFramePlugin() {
    return _framePlugin;
  }

  public Tellable getNetRef() {
    return _netRef;
  }

  private final JamverNetReceiveFrame _framePlugin;

  private final Tellable _netRef;
}
