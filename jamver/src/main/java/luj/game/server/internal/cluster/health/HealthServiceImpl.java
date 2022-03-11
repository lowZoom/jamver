package luj.game.server.internal.cluster.health;

import luj.game.server.api.cluster.ServerHealthListener;

final class HealthServiceImpl implements ServerHealthListener.Service {

  @Override
  public ServerHealthListener.Data data() {
    return _data;
  }

  DataServiceImpl _data;
}
