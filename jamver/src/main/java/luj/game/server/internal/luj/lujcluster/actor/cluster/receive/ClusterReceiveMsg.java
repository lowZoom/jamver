package luj.game.server.internal.luj.lujcluster.actor.cluster.receive;

import java.io.Serializable;

/**
 * 实际搭载集群消息到远端节点
 */
public class ClusterReceiveMsg implements Serializable {

  public ClusterReceiveMsg(String messageKey, byte[] messageData) {
    _messageKey = messageKey;
    _messageData = messageData;
  }

  public String getMessageKey() {
    return _messageKey;
  }

  public byte[] getMessageData() {
    return _messageData;
  }

  private final String _messageKey;

  private final byte[] _messageData;
}
