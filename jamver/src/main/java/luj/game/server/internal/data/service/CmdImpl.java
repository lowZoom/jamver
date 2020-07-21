package luj.game.server.internal.data.service;

import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execgroup.CmdGroupExecMsg;

final class CmdImpl implements CmdGroupExecMsg.Command {

  @Override
  public Class<?> type() {
    return _type;
  }

  @Override
  public Object param() {
    return _param;
  }

  Class<?> _type;

  Object _param;
}
