package luj.game.server.internal.data.instancev2;

import luj.game.server.internal.data.types.map.history.MapDataGetter;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public enum EntityFieldGetter {
  GET;

  public Object getValue(DataEntity entity, String field) {
    MapWithHistory<String, Object> valMap = entity.getFieldValueMap();
    return MapDataGetter.GET.getValue(valMap, field);
  }
}
