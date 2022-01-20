package luj.game.server.internal.cluster.join;

import luj.game.server.api.cluster.ServerJoinListener;

final class JoinContextImpl implements ServerJoinListener.Context {

  @Override
  public ServerJoinListener.Server remoteServer() {
    return _remoteServer;
  }

  @Override
  public ServerJoinListener.Service service() {
    return _service;
  }

  ServerJoinListener.Server _remoteServer;

  ServerJoinListener.Service _service;
}
