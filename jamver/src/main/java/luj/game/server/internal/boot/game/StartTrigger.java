package luj.game.server.internal.boot.game;

import static com.google.common.base.MoreObjects.firstNonNull;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.boot.GameStartListener;

public class StartTrigger {

  public StartTrigger(List<GameStartListener> startListenerList, Tellable dataRef) {
    _startListenerList = startListenerList;
    _dataRef = dataRef;
  }

  public void trigger() throws Exception {
    StartContextImpl ctx = new StartContextImpl();
    ctx._service = makeService();

    for (GameStartListener listener : nonNull(_startListenerList)) {
      listener.onStart(ctx);
    }
  }

  private GameStartListener.Service makeService() {
    ServiceData dataSvc = new ServiceData();
    dataSvc._dataRef = _dataRef;

    ServiceImpl service = new ServiceImpl();
    service._dataService = dataSvc;
    return service;
  }

  private <T> List<T> nonNull(List<T> list) {
    return firstNonNull(list, ImmutableList.of());
  }

  private final List<GameStartListener> _startListenerList;

  private final Tellable _dataRef;
}
