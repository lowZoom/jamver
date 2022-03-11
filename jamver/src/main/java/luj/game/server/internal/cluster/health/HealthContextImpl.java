package luj.game.server.internal.cluster.health;

import luj.game.server.api.cluster.ServerHealthListener;

final class HealthContextImpl implements ServerHealthListener.Context {

  @Override
  public ServerHealthListener.Server remoteServer() {
    return _server;
  }

  @Override
  public ServerHealthListener.Service service() {
    return _service;
  }

  ServerImpl _server;

  HealthServiceImpl _service;
}
