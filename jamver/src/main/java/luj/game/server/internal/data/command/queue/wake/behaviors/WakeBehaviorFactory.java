package luj.game.server.internal.data.command.queue.wake.behaviors;

import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.wake.QueueWakeBehavior;

public class WakeBehaviorFactory {

  public WakeBehaviorFactory(DataCommandRequest commandReq, CacheContainer dataCache,
      ActorMessageHandler.Ref dataRef, ActorPreStartHandler.Actor saveRef) {
    _commandReq = commandReq;
    _dataCache = dataCache;
    _dataRef = dataRef;
    _saveRef = saveRef;
  }

  public QueueWakeBehavior create() {
    if (_commandReq.getCommandKit() != null) {
      return new BehavCommand(this);
    }
    if (_commandReq.getCmdGroup() != null) {
      return new BehavGroup(this);
    }
    throw new IllegalStateException("无效请求");
  }

  final DataCommandRequest _commandReq;
  final CacheContainer _dataCache;

  final ActorMessageHandler.Ref _dataRef;
  final ActorPreStartHandler.Actor _saveRef;
}
