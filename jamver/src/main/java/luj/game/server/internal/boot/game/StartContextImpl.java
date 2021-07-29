package luj.game.server.internal.boot.game;

import luj.game.server.api.boot.GameStartListener;

final class StartContextImpl implements GameStartListener.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <P> P param() {
    return (P) _startParam;
  }

  @Override
  public GameStartListener.Service service() {
    return _service;
  }

  Object _startParam;

  GameStartListener.Service _service;
}
