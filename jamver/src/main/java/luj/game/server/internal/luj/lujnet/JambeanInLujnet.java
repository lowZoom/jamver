package luj.game.server.internal.luj.lujnet;

import java.util.concurrent.atomic.AtomicInteger;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.plugin.JamverNetReceiveFrame;

public class JambeanInLujnet {

  public JambeanInLujnet(JamverNetReceiveFrame framePlugin, Tellable netRef,
      AtomicInteger nextConnectionId) {
    _framePlugin = framePlugin;
    _netRef = netRef;
    _nextConnectionId = nextConnectionId;
  }

  public JamverNetReceiveFrame getFramePlugin() {
    return _framePlugin;
  }

  public Tellable getNetRef() {
    return _netRef;
  }

  public AtomicInteger getNextConnectionId() {
    return _nextConnectionId;
  }

  private final JamverNetReceiveFrame _framePlugin;
  private final Tellable _netRef;

  private final AtomicInteger _nextConnectionId;
}
