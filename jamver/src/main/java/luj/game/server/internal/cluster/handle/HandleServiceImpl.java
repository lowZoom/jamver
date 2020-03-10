package luj.game.server.internal.cluster.handle;

import luj.game.server.api.cluster.ServerMessageHandler;

final class HandleServiceImpl implements ServerMessageHandler.Service {

  HandleServiceImpl(DataServiceImpl dataService) {
    _dataService = dataService;
  }

  @Override
  public ServerMessageHandler.Data data() {
    return _dataService;
  }

  private final DataServiceImpl _dataService;
}
