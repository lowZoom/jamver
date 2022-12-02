package luj.game.server.internal.luj.lujcluster.actor.network.register.dynamic;

import java.util.Collection;
import luj.game.server.api.net.GameProtoHandler;

public record AddProtoHandleMsg(
    Collection<GameProtoHandler<?>> protoHandler) {
  // NOOP
}
