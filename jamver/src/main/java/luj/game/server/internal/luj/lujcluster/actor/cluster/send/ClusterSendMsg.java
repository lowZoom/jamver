package luj.game.server.internal.luj.lujcluster.actor.cluster.send;

import luj.cluster.api.node.NodeNewMemberListener;

/**
 * 在节点本地寻找能处理该消息的远程节点，以进行实际发送
 */
public class ClusterSendMsg {

  public ClusterSendMsg(String messageType, Object messageObj,
      NodeNewMemberListener.Node targetNode) {
    _messageType = messageType;
    _messageObj = messageObj;
    _targetNode = targetNode;
  }

  public String getMessageType() {
    return _messageType;
  }

  public Object getMessageObj() {
    return _messageObj;
  }

  public NodeNewMemberListener.Node getTargetNode() {
    return _targetNode;
  }

  private final String _messageType;
  private final Object _messageObj;

  private final NodeNewMemberListener.Node _targetNode;
}
