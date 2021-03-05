package luj.game.server.internal.network.proto.handle.invoke;

import luj.game.server.api.net.GameProtoHandler;

final class ConnectionImpl implements GameProtoHandler.Connection {

  @Override
  public Integer id() {
    return _id;
  }

  Integer _id;
}
