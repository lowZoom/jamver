package luj.game.server.internal.luj.lujcluster.actor.cluster.send;

/**
 * 在节点本地寻找能处理该消息的远程节点，以进行实际发送
 */
public class ClusterSendMsg {

  public ClusterSendMsg(String messageType, Object messageObj) {
    _messageType = messageType;
    _messageObj = messageObj;
  }

  public String getMessageType() {
    return _messageType;
  }

  public Object getMessageObj() {
    return _messageObj;
  }

  private final String _messageType;

  private final Object _messageObj;
}
