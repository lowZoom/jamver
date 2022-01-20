package luj.game.server.internal.cluster.handle;

import luj.game.server.api.cluster.ServerMessageHandler;

final class HandleServiceImpl implements ServerMessageHandler.Service {

  @Override
  public ServerMessageHandler.Data data() {
    return _dataService;
  }

  DataServiceImpl _dataService;
}
