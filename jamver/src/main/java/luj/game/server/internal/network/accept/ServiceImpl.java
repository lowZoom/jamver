package luj.game.server.internal.network.accept;

import luj.game.server.api.net.NetAcceptHandler;

final class ServiceImpl implements NetAcceptHandler.Service {

  @Override
  public NetAcceptHandler.Data data() {
    return _dataSvc;
  }

  ServiceData _dataSvc;
}
