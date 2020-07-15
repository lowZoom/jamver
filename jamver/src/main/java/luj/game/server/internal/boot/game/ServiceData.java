package luj.game.server.internal.boot.game;

import luj.cluster.api.actor.Tellable;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.DatacmdExecMsg;

final class ServiceData implements GameStartListener.Data {

  @Override
  public void executeCommand(Class<? extends GameDataCommand<?, ?>> commandType) {
    DatacmdExecMsg msg = new DatacmdExecMsg(commandType, null, null);
    _dataRef.tell(msg);
  }

  Tellable _dataRef;
}
