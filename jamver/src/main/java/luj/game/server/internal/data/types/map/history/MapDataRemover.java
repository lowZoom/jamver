package luj.game.server.internal.data.types.map.history;

import java.util.HashSet;
import java.util.Set;

public enum MapDataRemover {
  GET;

  public <K, V> V remove(MapWithHistory<K, V> map, K key) {
    V old = MapDataGetter.GET.getValue(map, key);
    if (old == null) {
      return null;
    }

    getOrNewRemoveHistory(map).add(key);
    //TODO: 要抵消更新历史

    return old;
  }

  private <K, V> Set<K> getOrNewRemoveHistory(MapWithHistory<K, V> map) {
    Set<K> old = map.getRemoveHistory();
    if (old != null) {
      return old;
    }
    Set<K> newHistory = new HashSet<>();
    map.setRemoveHistory(newHistory);
    return newHistory;
  }
}
