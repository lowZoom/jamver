package luj.game.server.internal.boot.game;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.command.service.CommandServiceFactory;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.DatacmdExecMsg;

final class ServiceData implements GameStartListener.Data {

  @Override
  public void executeCommand(Class<? extends GameDataCommand<?, ?>> commandType) {
    DatacmdExecMsg msg = new DatacmdExecMsg(commandType, null, null);
    _dataRef.tell(msg);
  }

  @Override
  public <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType) {
    return new CommandServiceFactory(_lujbean,
        _dataRef, _remoteRef, commandType, _commandMap).create();
  }

  Map<Class<?>, GameplayDataActor.CommandKit> _commandMap;
  BeanContext _lujbean;

  Tellable _dataRef;
  ServerMessageHandler.Server _remoteRef;
}
