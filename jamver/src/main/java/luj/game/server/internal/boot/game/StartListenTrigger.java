package luj.game.server.internal.boot.game;

import static com.google.common.base.MoreObjects.firstNonNull;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import luj.game.server.internal.luj.lujcluster.actor.start.JamStartActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartListenTrigger {

  public StartListenTrigger(JamStartActor actorState) {
    _actorState = actorState;
  }

  public void trigger() throws Exception {
    JambeanInLujcluster startParam = _actorState.getStartParam();
    List<GameStartListener> listnerList = nonNull(startParam.getStartListenerList());
    if (listnerList.isEmpty()) {
//      LOG.warn("[game]未发现任何GameStartListener");
      return;
    }

    StartContextImpl ctx = new StartContextImpl();
    ctx._startParam = startParam.getAppStartParam();
    ctx._service = makeService(startParam);

    for (GameStartListener listener : listnerList) {
      //TODO: 出错应该打断启动退出
      listener.onStart(ctx);
    }
  }

  private GameStartListener.Service makeService(JambeanInLujcluster startParam) {
    ServiceData dataSvc = new ServiceData();
    dataSvc._commandMap = _actorState.getCommandMap();
    dataSvc._lujbean = startParam.getLujbean();

    dataSvc._dataRef = _actorState.getRefCollection().getDataRef();
//    dataSvc._remoteRef =

    ServiceImpl service = new ServiceImpl();
    service._dataService = dataSvc;
    return service;
  }

  private <T> List<T> nonNull(List<T> list) {
    return firstNonNull(list, ImmutableList.of());
  }

//  private static final Logger LOG = LoggerFactory.getLogger(StartListenTrigger.class);

  private final JamStartActor _actorState;
}
