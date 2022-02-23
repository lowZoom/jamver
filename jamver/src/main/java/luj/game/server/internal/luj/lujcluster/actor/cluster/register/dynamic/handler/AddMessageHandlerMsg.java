package luj.game.server.internal.luj.lujcluster.actor.cluster.register.dynamic.handler;

import java.util.Collection;
import luj.game.server.api.cluster.ServerMessageHandler;

public class AddMessageHandlerMsg {

  public AddMessageHandlerMsg(Collection<ServerMessageHandler<?>> messageHandler) {
    _messageHandler = messageHandler;
  }

  public Collection<ServerMessageHandler<?>> getMessageHandler() {
    return _messageHandler;
  }

  private final Collection<ServerMessageHandler<?>> _messageHandler;
}
