package luj.game.server.internal.data.service;

import java.util.List;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execgroup.CmdGroupExecMsg;

final class MsgImpl implements CmdGroupExecMsg {

  @Override
  public Class<?> groupType() {
    return _groupType;
  }

  @Override
  public List<Command> commands() {
    return _cmdList;
  }

  @Override
  public ServerMessageHandler.Server remoteRef() {
    return _remoteRef;
  }

  Class<?> _groupType;
  List<Command> _cmdList;

  ServerMessageHandler.Server _remoteRef;
}
