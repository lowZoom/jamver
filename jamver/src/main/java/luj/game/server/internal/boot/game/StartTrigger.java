package luj.game.server.internal.boot.game;

import static com.google.common.base.MoreObjects.firstNonNull;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.api.boot.GameStartListener;

public class StartTrigger {

  public StartTrigger(List<GameStartListener> startListenerList) {
    _startListenerList = startListenerList;
  }

  public void trigger() throws Exception {
    StartContextImpl ctx = new StartContextImpl();

    for (GameStartListener listener : nonNull(_startListenerList)) {
      listener.onStart(ctx);
    }
  }

  private <T> List<T> nonNull(List<T> list) {
    return firstNonNull(list, ImmutableList.of());
  }

  private final List<GameStartListener> _startListenerList;
}
