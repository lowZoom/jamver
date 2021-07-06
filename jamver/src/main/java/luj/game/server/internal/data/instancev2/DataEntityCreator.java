package luj.game.server.internal.data.instancev2;

import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public class DataEntityCreator {

  public static DataEntityCreator create(Class<?> dataType, Comparable<?> dataId) {
    return new DataEntityCreator(dataType, dataId, ImmutableMap.of());
  }

  public DataEntityCreator(Class<?> dataType, Comparable<?> dataId, Map<String, Object> initValue) {
    _dataType = dataType;
    _dataId = dataId;
    _initValue = initValue;
  }

  public DataEntity create() {
    checkState(_dataType.isInterface(), _dataType);
    HashMap<String, Object> dataMap = new HashMap<>(_initValue);

    //FIXME: TEMP
    DataType type = new DataType(_dataType.getName(), false);

    return new DataEntity(type, _dataId, new MapWithHistory<>(dataMap));
  }

  private final Class<?> _dataType;

  private final Comparable<?> _dataId;
  private final Map<String, Object> _initValue;
}
