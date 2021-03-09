package luj.game.server.internal.network.disconnect;

import luj.game.server.api.net.GameDisconnectHandler;

final class ServiceImpl implements GameDisconnectHandler.Service {

  @Override
  public GameDisconnectHandler.Data data() {
    return _dataSvc;
  }

  ServiceData _dataSvc;
}
