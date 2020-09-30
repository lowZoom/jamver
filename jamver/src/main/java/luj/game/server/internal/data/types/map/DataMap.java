package luj.game.server.internal.data.types.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import luj.game.server.internal.data.types.HasOp;
import luj.game.server.internal.data.types.map.history.MapDataUpdater;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public class DataMap<K, V> implements Map<K, V>, HasOp {

  DataMap(MapWithHistory<K, V> data, MapOp op) {
    _data = data;
    _op = op;
  }

  @Override
  public int size() {
    return _data.getData().size();
  }

  @Override
  public boolean isEmpty() {
    return _data.getData().isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return _data.getData().containsKey(key);
  }

  @Override
  public V get(Object key) {
    return _data.getData().get(key);
  }

  @Override
  public V getOrDefault(Object key, V defaultValue) {
    return _data.getData().getOrDefault(key, defaultValue);
  }

  @Override
  public Set<K> keySet() {
    return _data.getData().keySet();
  }

  @Override
  public Collection<V> values() {
    return _data.getData().values();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return _data.getData().entrySet();
  }

  @Override
  public void forEach(BiConsumer<? super K, ? super V> action) {
    _data.getData().forEach(action);
  }

  @Override
  public V put(K key, V value) {
    V old = MapDataUpdater.SINGLETON.update(_data, key, value);
    _dirtyMarker.run();
    return old;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    _data.getData().putAll(m);

    _dirtyMarker.run();
    //TODO: 增加修改历史
  }

  @Override
  public V remove(Object key) {
    V old = _data.getData().remove(key);
    _dirtyMarker.run();
    return old;
  }

  @Override
  public void clear() {
    _data.getData().clear();
    _dirtyMarker.run();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getDataOp() {
    return (T) _op;
  }

  @Override
  public String toString() {
    return _data.getData().toString();
  }

  @Deprecated
  @Override
  public boolean containsValue(Object value) {
    throw new UnsupportedOperationException("containsValue");
  }

  @Deprecated
  @Override
  public V putIfAbsent(K key, V value) {
    throw new UnsupportedOperationException("putIfAbsent");
  }

  @Deprecated
  @Override
  public boolean remove(Object key, Object value) {
    throw new UnsupportedOperationException("remove");
  }

  @Deprecated
  @Override
  public boolean replace(K key, V oldValue, V newValue) {
    throw new UnsupportedOperationException("replace");
  }

  @Deprecated
  @Override
  public V replace(K key, V value) {
    throw new UnsupportedOperationException("replace");
  }

  @Deprecated
  @Override
  public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
    throw new UnsupportedOperationException("replaceAll");
  }

  @Deprecated
  @Override
  public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
    throw new UnsupportedOperationException("computeIfAbsent");
  }

  @Deprecated
  @Override
  public V computeIfPresent(K key,
      BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
    throw new UnsupportedOperationException("computeIfPresent");
  }

  @Deprecated
  @Override
  public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
    throw new UnsupportedOperationException("compute");
  }

  @Deprecated
  @Override
  public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
    throw new UnsupportedOperationException("merge");
  }

  Runnable _dirtyMarker;

  final MapWithHistory<K, V> _data;
  final MapOp _op;
}
