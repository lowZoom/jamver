package luj.game.server.internal.data.execute;

import luj.config.api.container.ConfigContainer;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.execute.service.config.ConfigServiceFactory;
import luj.game.server.internal.data.execute.service.event.EventServiceFactory;
import luj.game.server.internal.data.execute.service.time.TimeServiceFactory;

final class DataCmdServiceImpl implements GameDataCommand.Service {

  @Override
  public GameDataCommand.Data data() {
    return _dataSvc;
  }

  @Override
  public GameDataCommand.Proto proto() {
    return _protoSvc;
  }

  @Override
  public <C> GameDataCommand.Config<C> config(Class<C> configType) {
    return ConfigServiceFactory.GET.create(configType, configType.getName(), _configs);
  }

  @Override
  public GameDataCommand.EventOld event() {
    throw new UnsupportedOperationException("event已废弃");
  }

  @Override
  public <E> GameDataCommand.Event<E> event(Class<E> eventType) {
    return _eventSvc.create(eventType);
  }

  @Override
  public GameDataCommand.Network network() {
    return _networkSvc;
  }

  @Override
  public GameDataCommand.Random random() {
    throw new UnsupportedOperationException("random尚未实现");
  }

  @Override
  public GameDataCommand.Time time() {
    return TimeServiceFactory.GET.create();
  }

  GameDataCommand.Data _dataSvc;

  GameDataCommand.Proto _protoSvc;
  GameDataCommand.Network _networkSvc;

  ConfigContainer _configs;
  EventServiceFactory _eventSvc;
}
