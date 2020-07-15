package luj.game.server.internal.boot.game;

import luj.game.server.api.boot.GameStartListener;

final class StartContextImpl implements GameStartListener.Context {

  @Override
  public GameStartListener.Service service() {
    return _service;
  }

  GameStartListener.Service _service;
}
