package luj.game.server.internal.data.execute;

import luj.game.server.api.data.GameDataCommand;

final class CommandServiceImpl implements GameDataCommand.Service {

  CommandServiceImpl(GameDataCommand.Data dataSvc) {
    _dataSvc = dataSvc;
  }

  @Override
  public GameDataCommand.Data data() {
    return _dataSvc;
  }

  @Override
  public <C> GameDataCommand.Config<C> config(Class<C> configType) {
    return null;
  }

  @Override
  public GameDataCommand.Random random() {
    return null;
  }

  @Override
  public GameDataCommand.Network network() {
    return null;
  }

  private final GameDataCommand.Data _dataSvc;
}
