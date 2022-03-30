package luj.game.server.internal.data.command.queue.wake.behaviors;

import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.config.api.container.ConfigContainer;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.command.queue.wake.QueueWakeBehavior;
import luj.game.server.internal.data.id.state.DataIdGenState;

public class WakeBehaviorFactory {

  public WakeBehaviorFactory(DataCommandRequest commandReq, CacheContainer dataCache,
      DataIdGenState idGenState, ConfigContainer configs, Tellable dataRef, Tellable saveRef,
      BeanContext lujbean) {
    _commandReq = commandReq;
    _dataCache = dataCache;
    _idGenState = idGenState;
    _configs = configs;
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
  final DataIdGenState _idGenState;
  final ConfigContainer _configs;

  final Tellable _dataRef;
  final Tellable _saveRef;

  final BeanContext _lujbean;
}
