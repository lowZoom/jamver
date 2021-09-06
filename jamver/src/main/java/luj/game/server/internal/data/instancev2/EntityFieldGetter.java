package luj.game.server.internal.data.instancev2;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import luj.game.server.internal.data.types.map.DataMapFactory;
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
    if (fieldType == Integer.class || fieldType == Long.class) {
      return null;
    }

    Object newValue = getInitValue(fieldType, entity, field);
    MapDataUpdater.GET.update(valMap, field, newValue);
    return newValue;
  }

  private Object getInitValue(Class<?> type, DataEntity data, String fieldName) {
    if (type == int.class) {
      return INT_ZERO;
    }
    if (type == long.class) {
      return LONG_ZERO;
    }
    if (type == double.class) {
      return DOUBLE_ZERO;
    }
    if (type == String.class) {
      return "";
    }
    if (type == Set.class) {
      return DataSetFactory.GET.create();
    }
    if (type == Map.class) {
      return DataMapFactory.GET.create();
    }
    if (data.getDataType().isTransient()) {
      return null;
    }
    throw new UnsupportedOperationException("未知类型：" + type.getName()
        + "，字段：" + data.getDataType().getName() + "#" + fieldName);
  }

  private static final Integer INT_ZERO = 0;
  private static final Long LONG_ZERO = 0L;

  private static final Double DOUBLE_ZERO = 0.0;
}
