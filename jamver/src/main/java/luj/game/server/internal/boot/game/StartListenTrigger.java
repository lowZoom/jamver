package luj.game.server.internal.boot.game;

import static com.google.common.base.MoreObjects.firstNonNull;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import luj.game.server.internal.luj.lujcluster.actor.start.JamStartActor;

public class StartListenTrigger {

  public StartListenTrigger(JamStartActor actorState) {
    _actorState = actorState;
  }

  public void trigger() throws Exception {
    StartContextImpl ctx = new StartContextImpl();
    JambeanInLujcluster startParam = _actorState.getStartParam();

    ctx._service = makeService(startParam);
    List<GameStartListener> listnerList = startParam.getStartListenerList();

    for (GameStartListener listener : nonNull(listnerList)) {
      listener.onStart(ctx);
    }
  }

  private GameStartListener.Service makeService(JambeanInLujcluster startParam) {
    ServiceData dataSvc = new ServiceData();

    ServiceImpl service = new ServiceImpl();
    service._dataService = dataSvc;

    dataSvc._commandMap = _actorState.getCommandMap();
    dataSvc._lujbean = startParam.getLujbean();

    dataSvc._dataRef = _actorState.getRefCollection().getDataRef();
//    dataSvc._remoteRef =

    return service;
  }

  private <T> List<T> nonNull(List<T> list) {
    return firstNonNull(list, ImmutableList.of());
  }

  private final JamStartActor _actorState;
}
