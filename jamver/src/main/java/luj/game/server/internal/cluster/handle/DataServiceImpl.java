package luj.game.server.internal.cluster.handle;

import java.util.Map;
import java.util.function.Consumer;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.command.service.CommandServiceFactory;
import luj.game.server.internal.data.service.execute.GroupExecuteRequestor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.prepare.DatacmdExecMsg;

final class DataServiceImpl implements ServerMessageHandler.Data {

  @Override
  public <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType) {
    return new CommandServiceFactory(_lujbean, _dataRef,
        _remoteRef, commandType, _commandMap).create();
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.prepare.OnDataCmdExec
   */
  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param) {
    DatacmdExecMsg msg = new DatacmdExecMsg(commandType.getName(), param, _remoteRef);
    _dataRef.tell(msg);
  }

  @Override
  public void executeCommandGroup(Class<? extends GameDataCommandGroup> type,
      Consumer<Group> group) {
    new GroupExecuteRequestor(type, group, _remoteRef, _dataRef).request();
  }

  Map<String, GameplayDataActor.CommandKit> _commandMap;
  BeanContext _lujbean;

  Tellable _dataRef;
  ServerMessageHandler.Server _remoteRef;
}
