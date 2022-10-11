package luj.game.server.internal.boot.game;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import luj.game.server.internal.luj.lujcluster.actor.start.JamStartActor;

public class StartListenTrigger {

  public static StartListenTrigger get(JamStartActor actorState,
      List<GameStartListener> listenerList) {
    JambeanInLujcluster startParam = actorState.getStartParam();

    ServiceData dataSvc = new ServiceData();
    dataSvc._commandMap = actorState.getCommandMap();
    dataSvc._lujbean = startParam.getLujbean();
    dataSvc._dataRef = actorState.getDataRef();

    return new StartListenTrigger(listenerList, startParam.getAppStartParam(), dataSvc);
  }

  public StartListenTrigger(List<GameStartListener> listenerList, Object startParam,
      GameStartListener.Data dataService) {
    _listenerList = listenerList;
    _startParam = startParam;
    _dataService = dataService;
  }

  public void trigger() throws Exception {
    List<GameStartListener> listnerList = nonNull(_listenerList);
    if (listnerList.isEmpty()) {
//      LOG.warn("[game]未发现任何GameStartListener");
      return;
    }

    StartContextImpl ctx = new StartContextImpl();
    ctx._startParam = _startParam;
    ctx._service = makeService();

    for (GameStartListener listener : listnerList) {
      listener.onStart(ctx);
    }
  }

  private GameStartListener.Service makeService() {
    ServiceImpl service = new ServiceImpl();
    service._dataService = _dataService;
    return service;
  }

  private <T> List<T> nonNull(List<T> list) {
    return MoreObjects.firstNonNull(list, ImmutableList.of());
  }

//  private static final Logger LOG = LoggerFactory.getLogger(StartListenTrigger.class);

  private final List<GameStartListener> _listenerList;

  private final Object _startParam;
  private final GameStartListener.Data _dataService;
}
