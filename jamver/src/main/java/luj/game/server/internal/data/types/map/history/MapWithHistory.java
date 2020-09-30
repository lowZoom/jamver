package luj.game.server.internal.data.types.map.history;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapWithHistory<K, V> {

  public MapWithHistory(HashMap<K, V> data) {
    _data = data;
  }

  public Map<K, V> getUpdateHistory() {
    return _updateHistory;
  }

  public void setUpdateHistory(Map<K, V> updateHistory) {
    _updateHistory = updateHistory;
  }

  public Set<K> getRemoveHistory() {
    return _removeHistory;
  }

  public void setRemoveHistory(Set<K> removeHistory) {
    _removeHistory = removeHistory;
  }

  public HashMap<K, V> getData() {
    return _data;
  }

  private Map<K, V> _updateHistory;
  private Set<K> _removeHistory;

  private final HashMap<K, V> _data;
}
