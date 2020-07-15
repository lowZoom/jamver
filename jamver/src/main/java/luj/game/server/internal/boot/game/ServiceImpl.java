package luj.game.server.internal.boot.game;

import luj.game.server.api.boot.GameStartListener;

final class ServiceImpl implements GameStartListener.Service {

  @Override
  public GameStartListener.Data data() {
    return _dataService;
  }

  GameStartListener.Data _dataService;
}
