package luj.game.server.internal.luj.lujcluster.actor.network;

import luj.game.server.api.plugin.JamverNetAcceptInit;
import luj.game.server.api.plugin.JamverNetReceiveFrame;
import luj.game.server.api.plugin.JamverNetReceivePacket;
import luj.game.server.api.plugin.JamverNetSendEncode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see luj.game.server.internal.inject.ServerBeanCollector#collect
 */
public class NetAllPlugin {

  public JamverNetAcceptInit getAcceptInit() {
    return _acceptInit;
  }

  public JamverNetReceiveFrame getReceiveFrame() {
    return _receiveFrame;
  }

  public JamverNetReceivePacket<?> getReceivePacket() {
    return _receivePacket;
  }

  public JamverNetSendEncode getSendEncode() {
    return _sendEncode;
  }

  @Autowired(required = false)
  private JamverNetAcceptInit _acceptInit;

  @Autowired(required = false)
  private JamverNetReceiveFrame _receiveFrame;

  @Autowired(required = false)
  private JamverNetReceivePacket<?> _receivePacket;

  @Autowired(required = false)
  private JamverNetSendEncode _sendEncode;
}
