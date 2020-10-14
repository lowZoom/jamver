package luj.game.server.internal.data.types.map.history;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

public enum MapDataUpdater {
  GET;

  public <K, V> V update(MapWithHistory<K, V> map, K key, V value) {
    HashMap<K, V> data = map.getData();
//    if (old == value) {
//      return;
//    }

    V old = getOrNewUpdateHistory(map).put(checkNotNull(key), value);
    //TODO: 消除重复

    return (old == null) ? data.get(key) : old;
  }

  private <K, V> Map<K, V> getOrNewUpdateHistory(MapWithHistory<K, V> map) {
    Map<K, V> old = map.getUpdateHistory();
    if (old != null) {
      return old;
    }
    Map<K, V> newHistory = new HashMap<>();
    map.setUpdateHistory(newHistory);
    return newHistory;
  }
}
