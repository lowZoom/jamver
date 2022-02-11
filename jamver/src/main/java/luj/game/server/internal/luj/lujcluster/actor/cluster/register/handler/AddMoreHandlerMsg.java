package luj.game.server.internal.luj.lujcluster.actor.cluster.register.handler;

import java.util.Collection;
import luj.game.server.api.cluster.ServerMessageHandler;

public class AddMoreHandlerMsg {

  public AddMoreHandlerMsg(Collection<ServerMessageHandler<?>> messageHandler) {
    _messageHandler = messageHandler;
  }

  public Collection<ServerMessageHandler<?>> getMessageHandler() {
    return _messageHandler;
  }

  private final Collection<ServerMessageHandler<?>> _messageHandler;
}
