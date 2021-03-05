package luj.game.server.internal.network.proto.handle.invoke;

import luj.game.server.api.net.GameProtoHandler;

final class HandleServiceImpl implements GameProtoHandler.Service {

  @Override
  public <C> GameProtoHandler.Config<C> config(Class<C> configType) {
    throw new UnsupportedOperationException("config尚未实现");
  }

  @Override
  public GameProtoHandler.Data data() {
    return _dataSvc;
  }

  DataServiceImpl _dataSvc;
}
