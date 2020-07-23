package luj.game.server.internal.cluster.handle;

import java.util.function.Consumer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.service.execute.GroupExecuteRequestor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.DatacmdExecMsg;

final class DataServiceImpl implements ServerMessageHandler.Data {

  DataServiceImpl(Tellable dataRef, ServerMessageHandler.Server remoteRef) {
    _dataRef = dataRef;
    _remoteRef = remoteRef;
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.OnDataCcmdExec
   */
  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param) {
    DatacmdExecMsg msg = new DatacmdExecMsg(commandType, param, _remoteRef);
    _dataRef.tell(msg);
  }

  @Override
  public void executeCommandGroup(Class<? extends GameDataCommandGroup> type,
      Consumer<Group> group) {
    new GroupExecuteRequestor(type, group, _remoteRef, _dataRef).request();
  }

  private final Tellable _dataRef;

  private final ServerMessageHandler.Server _remoteRef;
}
