package luj.game.server.internal.data.execute;

import luj.game.server.api.data.GameDataCommand;

final class CmdServiceImpl implements GameDataCommand.Service {

  CmdServiceImpl(GameDataCommand.Data dataSvc, GameDataCommand.Event eventSvc) {
    _dataSvc = dataSvc;
    _eventSvc = eventSvc;
  }

  @Override
  public GameDataCommand.Data data() {
    return _dataSvc;
  }

  @Override
  public GameDataCommand.Proto proto() {
    return null;
  }

  @Override
  public <C> GameDataCommand.Config<C> config(Class<C> configType) {
    return null;
  }

  @Override
  public GameDataCommand.Event event() {
    return _eventSvc;
  }

  @Override
  public GameDataCommand.Network network() {
    return null;
  }

  @Override
  public GameDataCommand.Random random() {
    return null;
  }

  @Override
  public GameDataCommand.Time time() {
    return null;
  }

  private final GameDataCommand.Data _dataSvc;

  private final GameDataCommand.Event _eventSvc;
}
