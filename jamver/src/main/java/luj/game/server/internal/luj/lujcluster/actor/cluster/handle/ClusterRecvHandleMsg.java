package luj.game.server.internal.luj.lujcluster.actor.cluster.handle;

import luj.cluster.api.actor.ActorMessageHandler;

public class ClusterRecvHandleMsg {

  public ClusterRecvHandleMsg(String messageKey, Object messageObj,
      ActorMessageHandler.Node remoteNode) {
    _messageKey = messageKey;
    _messageObj = messageObj;
    _remoteNode = remoteNode;
  }

  public String getMessageKey() {
    return _messageKey;
  }

  public Object getMessageObj() {
    return _messageObj;
  }

  public ActorMessageHandler.Node getRemoteNode() {
    return _remoteNode;
  }

  private final String _messageKey;
  private final Object _messageObj;

  private final ActorMessageHandler.Node _remoteNode;
}
