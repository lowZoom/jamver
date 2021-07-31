package luj.game.server.internal.data.instancev2;

import luj.game.server.internal.data.load.result.DataResultProxyV2;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public class DataEntity {

  public DataEntity(DataType dataType, Comparable<?> dataId,
      MapWithHistory<String, Object> fieldValueMap) {
    _dataType = dataType;
    _dataId = dataId;
    _fieldValueMap = fieldValueMap;
  }

  public DataResultProxyV2 getResultCache() {
    return _resultCache;
  }

  public void setResultCache(DataResultProxyV2 resultCache) {
    _resultCache = resultCache;
  }

  public DataType getDataType() {
    return _dataType;
  }

  public Comparable<?> getDataId() {
    return _dataId;
  }

  public MapWithHistory<String, Object> getFieldValueMap() {
    return _fieldValueMap;
  }

  private DataResultProxyV2 _resultCache;

  private final DataType _dataType;

  private final Comparable<?> _dataId;
  private final MapWithHistory<String, Object> _fieldValueMap;
}
