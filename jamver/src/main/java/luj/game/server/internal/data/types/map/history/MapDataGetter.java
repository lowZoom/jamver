package luj.game.server.internal.data.types.map.history;

public enum MapDataGetter {
  GET;

  public <K, V> V getValue(MapWithHistory<K, V> map, K key) {
    return map.getData().get(key);
  }
}
