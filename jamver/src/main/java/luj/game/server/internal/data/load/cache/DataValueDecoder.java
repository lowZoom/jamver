package luj.game.server.internal.data.load.cache;

import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import luj.game.server.internal.data.types.map.DataMap;
import luj.game.server.internal.data.types.map.DataMapFactory;
import luj.game.server.internal.data.types.set.DataSet;
import luj.game.server.internal.data.types.set.DataSetFactory;

/**
 * 将数据库读出来的普通数据类型，转成带修改历史的数据类型
 */
final class DataValueDecoder {

  DataValueDecoder(Map<String, Object> rawValue) {
    _rawValue = rawValue;
  }

  Map<String, Object> decode() {
    Map<String, ?> setType = _rawValue.entrySet().stream()
        .filter(e -> e.getValue() instanceof Set)
        .collect(toMap(Map.Entry::getKey, e -> decodeSet((Set<?>) e.getValue())));

    Map<String, ?> mapType = _rawValue.entrySet().stream()
        .filter(e -> e.getValue() instanceof Map)
        .collect(toMap(Map.Entry::getKey, e -> decodeMap((Map<?, ?>) e.getValue())));

    Map<String, Object> result = new HashMap<>(_rawValue);
    result.putAll(setType);
    result.putAll(mapType);

    return result;
  }

  private Set<?> decodeSet(Set<?> raw) {
    checkState(!(raw instanceof DataSet));
    return DataSetFactory.GET.create(new LinkedHashSet<>(raw));
  }

  private Map<?, ?> decodeMap(Map<?, ?> raw) {
    checkState(!(raw instanceof DataMap));
    return DataMapFactory.GET.create(new HashMap<>(raw));
  }

  private final Map<String, Object> _rawValue;
}
