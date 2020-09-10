package luj.game.server.internal.data.load.cache;

import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.Map;
import luj.game.server.internal.data.types.map.DataMap;
import luj.game.server.internal.data.types.map.DataMapFactory;

final class DataValueDecoder {

  DataValueDecoder(Map<String, Object> rawValue) {
    _rawValue = rawValue;
  }

  Map<String, Object> decode() {
    Map<String, ?> mapType = _rawValue.entrySet().stream()
        .filter(e -> e.getValue() instanceof Map)
        .collect(toMap(Map.Entry::getKey, e -> decodeMap((Map<?, ?>) e.getValue())));

    Map<String, Object> result = new HashMap<>(_rawValue);
    result.putAll(mapType);
    return result;
  }

  private Map<?, ?> decodeMap(Map<?, ?> raw) {
    checkState(!(raw instanceof DataMap));
    return DataMapFactory.SINGLETON.create(new HashMap<>(raw));
  }

  private final Map<String, Object> _rawValue;
}
