package luj.game.server.internal.data.types.map.history;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Set;
import luj.ava.collection.CollectionX;
import luj.ava.collection.map.MapX;

public enum MapModificationApplier {
  GET;

  @SuppressWarnings("unchecked")
  public <K> void apply(MapWithHistory<K, ?> map) {
    MapWithHistory<K, Object> mapVar = (MapWithHistory<K, Object>) map;
    Map<K, Object> value = mapVar.getData();

    Set<K> removeHistory = CollectionX.nonNull(mapVar.getRemoveHistory());
    for (K key : removeHistory) {
      Object old = value.remove(key);
      checkNotNull(old);
    }

    Map<K, Object> updateHistory = MapX.nonNull(mapVar.getUpdateHistory());
    value.putAll(updateHistory);

    mapVar.setUpdateHistory(null);
    mapVar.setRemoveHistory(null);
  }
}
