package luj.game.server.internal.data.types.map;

import java.util.HashMap;
import java.util.Map;

public enum DataMapFactory {
  SINGLETON;

  public <K, V> Map<K, V> create() {
    return create(new HashMap<>());
  }

  public <K, V> Map<K, V> create(HashMap<K, V> value) {
    MapOp op = new MapOp();

    DataMap<K, V> map = new DataMap<>(value, op);
    op._map = map;

    return map;
  }
}
