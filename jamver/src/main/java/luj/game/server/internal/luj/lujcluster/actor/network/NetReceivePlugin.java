package luj.game.server.internal.luj.lujcluster.actor.network;

import luj.game.server.api.plugin.JamverNetReceiveFrame;
import luj.game.server.api.plugin.JamverNetReceivePacket;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see luj.game.server.internal.inject.ServerBeanCollector#collect
 */
public class NetReceivePlugin {

  public JamverNetReceiveFrame getReceiveFrame() {
    return _receiveFrame;
  }

  public JamverNetReceivePacket<?> getReceivePacket() {
    return _receivePacket;
  }

  @Autowired
  private JamverNetReceiveFrame _receiveFrame;

  @Autowired
  private JamverNetReceivePacket<?> _receivePacket;
}
