package luj.game.server.internal.data.command.queue.add;

import java.util.List;
import java.util.Queue;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.command.queue.CommandRequestFactory;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;

public class WaitQueueGroupAdder {

  public WaitQueueGroupAdder(Queue<DataCommandRequest> waitQueue, GameDataCommandGroup cmdGroup,
      List<GroupReqElement> elemList, ServerMessageHandler.Server remoteRef) {
    _waitQueue = waitQueue;
    _cmdGroup = cmdGroup;
    _elemList = elemList;
    _remoteRef = remoteRef;
  }

  public void addGroup() {
    DataCommandRequest commandReq = new CommandRequestFactory(
        _remoteRef).createGroup(_cmdGroup, _elemList);

    _waitQueue.add(commandReq);
  }

  private final Queue<DataCommandRequest> _waitQueue;

  private final GameDataCommandGroup _cmdGroup;
  private final List<GroupReqElement> _elemList;

  private final ServerMessageHandler.Server _remoteRef;
}
