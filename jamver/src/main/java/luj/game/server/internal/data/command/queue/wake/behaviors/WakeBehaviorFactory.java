package luj.game.server.internal.data.command.queue.wake.behaviors;

import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.wake.QueueWakeBehavior;

public class WakeBehaviorFactory {

  public WakeBehaviorFactory(DataCommandRequest commandReq, CacheContainer dataCache,
      Tellable dataRef, Tellable saveRef, BeanContext lujbean) {
    _commandReq = commandReq;
    _dataCache = dataCache;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _lujbean = lujbean;
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

  final Tellable _dataRef;
  final Tellable _saveRef;

  final BeanContext _lujbean;
}
