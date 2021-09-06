package luj.game.server.internal.data.execute;

import luj.bean.api.BeanContext;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.execute.service.proto.ProtoServiceFactory;
import luj.game.server.internal.data.execute.service.time.TimeServiceFactory;

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
  public GameDataCommand.EventOld event() {
    return _eventSvc;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E> GameDataCommand.Event<E> event(Class<E> eventType) {
    return (GameDataCommand.Event<E>) _eventSvc2;
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
  GameDataCommand.Network _networkSvc;

  GameDataCommand.EventOld _eventSvc;
  GameDataCommand.Event<?> _eventSvc2;

  BeanContext _lujbean;
}
