package luj.game.server.internal.data.service.execute;

import java.util.List;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execgroup.CmdGroupExecMsg.Command;

final class GroupImpl implements ServerMessageHandler.Data.Group {

  @Override
  public <P> ServerMessageHandler.Data.Group command(
      Class<? extends GameDataCommand<P, ?>> commandType, P param) {
    CmdImpl cmd = new CmdImpl();
    cmd._type = commandType;
    cmd._param = param;

    _cmdList.add(cmd);
    return this;
  }

  List<Command> _cmdList;
}
