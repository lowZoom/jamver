package luj.game.server.internal.event.listener.invoke;

import java.util.List;
import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListenInvoker {

  public EventListenInvoker(List<GameEventListener<?>> listenerList, Object event,
      GameEventListener.Service service, Tellable dataRef,
      Map<String, GameplayDataActor.CommandKit> commandMap, BeanContext lujbean) {
    _listenerList = listenerList;
    _event = event;
    _service = service;
    _dataRef = dataRef;
    _commandMap = commandMap;
    _lujbean = lujbean;
  }

  public void invoke() {
    ListenerContextImpl listenCtx = new ListenerContextImpl();
    listenCtx._event = _event;
    listenCtx._service = makeEventService();

    for (GameEventListener<?> listener : _listenerList) {
      //TODO: 异步单独执行
      invokeListener(listener, listenCtx);
    }
  }

  private void invokeListener(GameEventListener<?> listener, ListenerContextImpl ctx) {
    try {
      listener.onEvent(ctx);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    }
  }

  private GameEventListener.Service makeEventService() {
    if (_service != null) {
      return _service;
    }

    DataServiceImpl dataSvc = new DataServiceImpl();
    dataSvc._dataRef = _dataRef;
    dataSvc._commandMap = _commandMap;
    dataSvc._lujbean = _lujbean;

    ListenServiceImpl result = new ListenServiceImpl();
    result._dataSvc = dataSvc;

    return result;
  }

  private static final Logger LOG = LoggerFactory.getLogger(EventListenInvoker.class);

  private final List<GameEventListener<?>> _listenerList;

  private final Object _event;
  private final GameEventListener.Service _service;

  private final Tellable _dataRef;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final BeanContext _lujbean;
}
