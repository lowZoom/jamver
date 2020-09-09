package luj.game.server.internal.data.command.queue.wake.behaviors;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.cache.api.request.CacheRequest;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.wake.QueueWakeBehavior;
import luj.game.server.internal.data.execute.finish.CommandExecFinisher;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

final class BehavCommand implements QueueWakeBehavior {

  BehavCommand(WakeBehaviorFactory factory) {
    _factory = factory;
  }

  @Override
  public List<CacheRequest> getCacheReq() {
    DataCommandRequest commandReq = _factory._commandReq;
    return ImmutableList.of(commandReq.getCacheReq());
  }

  @Override
  public void finishExec() {
    DataCommandRequest commandReq = _factory._commandReq;
    GameplayDataActor.CommandKit cmdKit = commandReq.getCommandKit();

    new CommandExecFinisher(cmdKit.getLoadResultType(), commandReq.getCacheReq(),
        _factory._dataCache, cmdKit.getCommandType(), cmdKit, commandReq.getCommandParam(),
        _factory._dataRef, _factory._saveRef, commandReq.getRemoteRef(), _factory._lujbean)
        .finish();
  }

  private final WakeBehaviorFactory _factory;
}
