package luj.game.server.internal.cluster.join;

import luj.game.server.api.cluster.ServerJoinListener;

final class JoinServiceImpl implements ServerJoinListener.Service {

  @Override
  public ServerJoinListener.Data data() {
    return _dataService;
  }

  DataServiceImpl _dataService;
}
