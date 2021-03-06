package luj.game.server.internal.luj.lujnet;

import java.util.concurrent.atomic.AtomicInteger;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.plugin.JamverNetAcceptInit;
import luj.game.server.api.plugin.JamverNetReceiveFrame;

public class JambeanInLujnet {

  public JambeanInLujnet(JamverNetAcceptInit acceptInitPlugin,
      JamverNetReceiveFrame frameReceivePlugin, Tellable netRef, AtomicInteger nextConnectionId) {
    _acceptInitPlugin = acceptInitPlugin;
    _frameReceivePlugin = frameReceivePlugin;
    _netRef = netRef;
    _nextConnectionId = nextConnectionId;
  }

  public JamverNetReceiveFrame getFrameReceivePlugin() {
    return _frameReceivePlugin;
  }

  public JamverNetAcceptInit getAcceptInitPlugin() {
    return _acceptInitPlugin;
  }

  public Tellable getNetRef() {
    return _netRef;
  }

  public AtomicInteger getNextConnectionId() {
    return _nextConnectionId;
  }

  private final JamverNetAcceptInit _acceptInitPlugin;
  private final JamverNetReceiveFrame _frameReceivePlugin;

  private final Tellable _netRef;
  private final AtomicInteger _nextConnectionId;
}
