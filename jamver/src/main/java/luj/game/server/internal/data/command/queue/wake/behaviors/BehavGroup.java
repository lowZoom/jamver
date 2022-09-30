package luj.game.server.internal.data.command.queue.wake.behaviors;

import java.util.List;
import java.util.stream.Collectors;
import luj.cache.api.request.CacheRequest;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;
import luj.game.server.internal.data.command.queue.wake.QueueWakeBehavior;
import luj.game.server.internal.data.execute.group.GroupExecFinisher;

final class BehavGroup implements QueueWakeBehavior {

  BehavGroup(WakeBehaviorFactory factory) {
    _factory = factory;
  }

  @Override
  public List<CacheRequest> getCacheReq() {
    return _factory._commandReq.getGroupElemList().stream()
        .map(GroupReqElement::getCacheReq)
        .collect(Collectors.toList());
  }

  @Override
  public void finishExec() {
    DataCommandRequest commandReq = _factory._commandReq;

    new GroupExecFinisher(commandReq.getCmdGroup(), commandReq.getGroupElemList(),
        _factory._dataCache, _factory._idGenState, _factory._configs, _factory._dataRef,
        _factory._saveRef, _factory._eventRef, commandReq.getRemoteRef(),
        commandReq.getCommandMap(), _factory._lujbean).finish();
  }

  private final WakeBehaviorFactory _factory;
}
