package luj.game.server.internal.data.types.map.history;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

public enum MapInitEncoder {
  GET;

  public Map<?, ?> encode(MapWithHistory<?, ?> map) {
    return ImmutableMap.copyOf(map.getData());
  }
}
