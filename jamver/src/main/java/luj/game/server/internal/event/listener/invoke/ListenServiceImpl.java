package luj.game.server.internal.event.listener.invoke;

import luj.game.server.api.event.GameEventListener;

final class ListenServiceImpl implements GameEventListener.Service {

  @Override
  public GameEventListener.Data data() {
    return _dataSvc;
  }

  @Override
  public GameEventListener.Network network() {
    throw new UnsupportedOperationException("该接口已弃用");
  }

  DataServiceImpl _dataSvc;
}
