package luj.game.server.internal.event.listener.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import luj.game.server.api.event.GameEventListener;

public class ListenMapImmutableMaker {

  public ListenMapImmutableMaker(Map<String, List<GameEventListener<?>>> mutable) {
    _mutable = mutable;
  }

  public Map<String, List<GameEventListener<?>>> make() {
    ImmutableMap.Builder<String, List<GameEventListener<?>>> result = ImmutableMap.builder();

    for (Map.Entry<String, List<GameEventListener<?>>> e : _mutable.entrySet()) {
      String key = e.getKey();
      result.put(key, ImmutableList.copyOf(_mutable.get(key)));
    }
    return result.build();
  }

  private final Map<String, List<GameEventListener<?>>> _mutable;
}
