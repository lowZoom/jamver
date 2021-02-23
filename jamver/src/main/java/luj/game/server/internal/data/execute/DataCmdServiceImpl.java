package luj.game.server.internal.data.execute;

import luj.bean.api.BeanContext;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.execute.service.proto.ProtoServiceFactory;

final class DataCmdServiceImpl implements GameDataCommand.Service {

  @Override
  public GameDataCommand.Data data() {
    return _dataSvc;
  }

  @Override
  public GameDataCommand.Proto proto() {
    return new ProtoServiceFactory(_lujbean).create();
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
    return _networkSvc;
  }

  @Override
  public GameDataCommand.Random random() {
    return null;
  }

  @Override
  public GameDataCommand.Time time() {
    return null;
  }

  GameDataCommand.Data _dataSvc;

  GameDataCommand.Event _eventSvc;
  GameDataCommand.Network _networkSvc;

  BeanContext _lujbean;
}
