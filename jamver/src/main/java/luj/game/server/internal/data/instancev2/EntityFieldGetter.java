package luj.game.server.internal.data.instancev2;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import luj.game.server.internal.data.types.map.history.MapDataGetter;
import luj.game.server.internal.data.types.map.history.MapDataUpdater;
import luj.game.server.internal.data.types.map.history.MapWithHistory;
import luj.game.server.internal.data.types.set.DataSetFactory;

public enum EntityFieldGetter {
  GET;

  public Object getOrInitValue(DataEntity entity, String field, Map<String, Method> fieldMap) {
    MapWithHistory<String, Object> valMap = entity.getFieldValueMap();
    Object oldValue = MapDataGetter.GET.getValue(valMap, field);
    if (oldValue != null) {
      return oldValue;
    }

    Method fieldMethod = fieldMap.get(field);
    Class<?> fieldType = fieldMethod.getReturnType();

    Object newValue = getInitValue(fieldType);
    MapDataUpdater.GET.update(valMap, field, newValue);

    return newValue;
  }

  private Object getInitValue(Class<?> type) {
    if (type == int.class || type == Integer.class) {
      return INT_ZERO;
    }
    if (type == long.class || type == Long.class) {
      return LONG_ZERO;
    }
    if (type == Set.class) {
      return DataSetFactory.GET.create();
    }
    throw new UnsupportedOperationException("未知类型：" + type.getName());
  }

  private static final Integer INT_ZERO = 0;
  private static final Long LONG_ZERO = 0L;
}
