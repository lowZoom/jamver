package luj.game.server.internal.data.service.execute;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execgroup.CmdGroupExecMsg;

public class GroupExecuteRequestor {

  public GroupExecuteRequestor(Class<?> type,
      Consumer<ServerMessageHandler.Data.Group> groupBuilder,
      ServerMessageHandler.Server remoteRef, Tellable dataRef) {
    _type = type;
    _groupBuilder = groupBuilder;
    _remoteRef = remoteRef;
    _dataRef = dataRef;
  }

  public void request() {
    CmdGroupExecMsg msg = makeMsg();
    _dataRef.tell(msg);
  }

  private CmdGroupExecMsg makeMsg() {
    MsgImpl msg = new MsgImpl();
    msg._groupType = _type;
    msg._remoteRef = _remoteRef;

    List<CmdGroupExecMsg.Command> cmdList = new LinkedList<>();
    msg._cmdList = cmdList;

    GroupImpl group = new GroupImpl();
    group._cmdList = cmdList;
    _groupBuilder.accept(group);

    return msg;
  }

  private final Class<?> _type;
  private final Consumer<ServerMessageHandler.Data.Group> _groupBuilder;

  private final ServerMessageHandler.Server _remoteRef;
  private final Tellable _dataRef;
}
