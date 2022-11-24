package luj.game.server.internal.event.listener.invoke;

import luj.game.server.api.event.GameEventListener;

final class ListenServiceImpl implements GameEventListener.Service {

  @Override
  public GameEventListener.Data data() {
    return _dataSvc;
  }

  DataServiceImpl _dataSvc;
}
