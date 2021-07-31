package luj.game.server.internal.data.instancev2;

import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public class DataEntityCreator {

  public static DataEntityCreator create(DataType dataType, Comparable<?> dataId) {
    return new DataEntityCreator(dataType, dataId, ImmutableMap.of());
  }

  public DataEntityCreator(DataType dataType, Comparable<?> dataId, Map<String, Object> initValue) {
    _dataType = dataType;
    _dataId = dataId;
    _initValue = initValue;
  }

  public DataEntity create() {
    Class<?> dataClass = _dataType.getClassCache();

    checkState(dataClass.isInterface(), _dataType);
    HashMap<String, Object> dataMap = new HashMap<>(_initValue);

    return new DataEntity(_dataType, _dataId, new MapWithHistory<>(dataMap));
  }

  private final DataType _dataType;

  private final Comparable<?> _dataId;
  private final Map<String, Object> _initValue;
}
