package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execgroup;

import java.util.List;
import luj.game.server.api.cluster.ServerMessageHandler;

public interface CmdGroupExecMsg {

  interface Command {

    Class<?> type();

    Object param();
  }

  Class<?> groupType();

  List<Command> commands();

  ServerMessageHandler.Server remoteRef();
}
