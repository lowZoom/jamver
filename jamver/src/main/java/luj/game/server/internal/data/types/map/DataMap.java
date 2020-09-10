package luj.game.server.internal.data.types.map;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import luj.game.server.internal.data.types.HasOp;

public class DataMap<K, V> implements Map<K, V>, HasOp {

  DataMap(HashMap<K, V> value, MapOp op) {
    _value = value;
    _op = op;
  }

  @Override
  public int size() {
    return _value.size();
  }

  @Override
  public boolean isEmpty() {
    return _value.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return _value.containsKey(key);
  }

  @Override
  public V get(Object key) {
    return _value.get(key);
  }

  @Override
  public V getOrDefault(Object key, V defaultValue) {
    return _value.getOrDefault(key, defaultValue);
  }

  @Override
  public Set<K> keySet() {
    return _value.keySet();
  }

  @Override
  public Collection<V> values() {
    return _value.values();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return _value.entrySet();
  }

  @Override
  public void forEach(BiConsumer<? super K, ? super V> action) {
    _value.forEach(action);
  }

  @Override
  public V put(K key, V value) {
    V old = _value.put(checkNotNull(key), value);
    _dirtyMarker.run();
    return old;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    _value.putAll(m);
    _dirtyMarker.run();
  }

  @Override
  public V remove(Object key) {
    V old = _value.remove(key);
    _dirtyMarker.run();
    return old;
  }

  @Override
  public void clear() {
    _value.clear();
    _dirtyMarker.run();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getDataOp() {
    return (T) _op;
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

  final HashMap<K, V> _value;
  final MapOp _op;
}
