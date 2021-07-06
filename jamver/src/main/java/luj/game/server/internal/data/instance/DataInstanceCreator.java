package luj.game.server.internal.data.instance;

import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import luj.game.server.internal.data.types.map.DataMapFactory;
import luj.game.server.internal.data.types.map.history.MapWithHistory;
import luj.game.server.internal.data.types.set.DataSetFactory;

public class DataInstanceCreator {

  public static DataInstanceCreator create(Class<?> dataType) {
    return new DataInstanceCreator(dataType, ImmutableMap.of());
  }

  public DataInstanceCreator(Class<?> dataType, Map<String, Object> initValue) {
    _dataType = dataType;
    _initValue = initValue;
  }

  public DataTempProxy create() {
    checkState(_dataType.isInterface(), _dataType);
    HashMap<String, Object> dataMap = new HashMap<>(_initValue);

    List<Method> nullFields = Arrays.stream(_dataType.getMethods())
        .filter(m -> !_initValue.containsKey(m.getName()))
        .collect(Collectors.toList());

    dataMap.putAll(makeInitMap(nullFields, int.class, m -> INT_ZERO));
//    dataMap.putAll(makeInitMap(nullFields, List.class, m -> DataListFactory.GET.create()));
    dataMap.putAll(makeInitMap(nullFields, Set.class, m -> DataSetFactory.GET.create()));
    dataMap.putAll(makeInitMap(nullFields, Map.class, m -> DataMapFactory.GET.create()));

    return new DataTempProxy(_dataType, dataMap, new MapWithHistory<>(dataMap)).init();
  }

  private Map<String, Object> makeInitMap(List<Method> fieldList, Class<?> type,
      Function<Method, Object> valueInit) {
    return fieldList.stream()
        .filter(m -> m.getReturnType() == type)
        .collect(toMap(Method::getName, valueInit));
  }

  private static final Integer INT_ZERO = 0;

  private final Class<?> _dataType;
  private final Map<String, Object> _initValue;
}
