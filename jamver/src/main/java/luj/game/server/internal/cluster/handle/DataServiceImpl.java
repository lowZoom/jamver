package luj.game.server.internal.cluster.handle;

import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.execute.DatacmdExecMsg;

final class DataServiceImpl implements ServerMessageHandler.Data {

  DataServiceImpl(NodeStartListener.Actor dataRef) {
    _dataRef = dataRef;
  }

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param) {
    DatacmdExecMsg msg = new DatacmdExecMsg(commandType, param);
    _dataRef.tell(msg);
  }

  private final NodeStartListener.Actor _dataRef;
}
