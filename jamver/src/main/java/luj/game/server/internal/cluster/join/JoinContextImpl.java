package luj.game.server.internal.cluster.join;

import luj.game.server.api.cluster.ServerJoinListener;

final class JoinContextImpl implements ServerJoinListener.Context {

  JoinContextImpl(ServerJoinListener.Server remoteServer) {
    _remoteServer = remoteServer;
  }

  @Override
  public ServerJoinListener.Server remoteServer() {
    return _remoteServer;
  }

  private final ServerJoinListener.Server _remoteServer;
}
