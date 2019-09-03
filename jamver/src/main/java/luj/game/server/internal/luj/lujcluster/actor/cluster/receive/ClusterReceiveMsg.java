package luj.game.server.internal.luj.lujcluster.actor.cluster.receive;

import java.io.Serializable;

public class ClusterReceiveMsg implements Serializable {

  public ClusterReceiveMsg(String messageKey, Object message) {
    _messageKey = messageKey;
    _message = message;
  }

  public String getMessageKey() {
    return _messageKey;
  }

  public Object getMessage() {
    return _message;
  }

  private final String _messageKey;

  private final Object _message;
}
