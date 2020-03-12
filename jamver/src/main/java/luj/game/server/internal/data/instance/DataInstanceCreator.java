package luj.game.server.internal.data.instance;

import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class DataInstanceCreator {

  public DataInstanceCreator(Class<?> dataType) {
    _dataType = dataType;
  }

  public DataTempProxy create() {
    checkState(_dataType.isInterface(), _dataType);
    Method[] methodList = _dataType.getMethods();

    return new DataTempProxy(_dataType, new HashMap<>(ImmutableMap.<String, Object>builder()
        .putAll(makeInitMap(methodList, List.class, m -> new ArrayList<>()))
        .putAll(makeInitMap(methodList, Set.class, m -> new HashSet<>()))
        .putAll(makeInitMap(methodList, int.class, m -> INT_ZERO))
        .build())).init();
  }

  private Map<String, Object> makeInitMap(Method[] methodList, Class<?> type,
      Function<Method, Object> valueInit) {
    return Arrays.stream(methodList)
        .filter(m -> m.getReturnType() == type)
        .collect(toMap(Method::getName, valueInit));
  }

  private static final Integer INT_ZERO = 0;

  private final Class<?> _dataType;
}
