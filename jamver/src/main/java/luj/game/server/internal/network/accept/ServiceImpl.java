package luj.game.server.internal.network.accept;

import luj.game.server.api.net.GameAcceptHandler;

final class ServiceImpl implements GameAcceptHandler.Service {

  @Override
  public GameAcceptHandler.Data data() {
    return _dataSvc;
  }

  ServiceData _dataSvc;
}
