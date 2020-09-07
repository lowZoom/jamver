package luj.game.server.internal.luj.lujcluster.actor.cluster.register.handle;

import java.io.Serializable;
import java.util.Set;

public class JamRegisterHandleMsg implements Serializable {

  public JamRegisterHandleMsg(Set<String> messageCanHandle) {
    _messageCanHandle = messageCanHandle;
  }

  public Set<String> getMessageCanHandle() {
    return _messageCanHandle;
  }

  private final Set<String> _messageCanHandle;
}
