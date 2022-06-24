package luj.game.server.internal.network.proto.handle.invoke;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.data.command.service.CommandServiceFactory;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

final class DataServiceImpl implements GameProtoHandler.Data {

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param) {
    throw new UnsupportedOperationException("executeCommand已废弃");
  }

  @Override
  public <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType) {
    return new CommandServiceFactory(_lujbean, _dataRef,
        null, commandType, _commandMap).create();
  }

  Map<String, GameplayDataActor.CommandKit> _commandMap;
  BeanContext _lujbean;

  Tellable _dataRef;
}
