package luj.game.server.internal.luj.lujcluster.actor.cluster.register.dynamic.handler;

import java.util.Collection;
import luj.game.server.api.cluster.ServerMessageHandler;

public record AddMessageHandlerMsg(
    Collection<ServerMessageHandler<?>> messageHandler) {
  // NOOP
}
