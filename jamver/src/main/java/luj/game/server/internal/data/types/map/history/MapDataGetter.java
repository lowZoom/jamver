package luj.game.server.internal.data.types.map.history;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

public enum MapDataGetter {
  GET;

  public <K, V> V getValue(MapWithHistory<K, V> map, K key) {
    Map<K, V> updateHistory = MoreObjects.firstNonNull(map.getUpdateHistory(), ImmutableMap.of());
    V updateVal = updateHistory.get(key);
    if (updateVal != null) {
      return updateVal;
    }

    return map.getData().get(key);
  }
}
