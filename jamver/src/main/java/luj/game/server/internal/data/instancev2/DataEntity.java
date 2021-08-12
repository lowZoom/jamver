package luj.game.server.internal.data.instancev2;

import luj.game.server.internal.data.load.result.DataResultProxyV2;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public class DataEntity {

  public DataEntity(DataType dataType, MapWithHistory<String, Object> fieldValueMap) {
    _dataType = dataType;
    _fieldValueMap = fieldValueMap;
  }

  public Comparable<?> getDataId() {
    return _dataId;
  }

  public void setDataId(Comparable<?> dataId) {
    _dataId = dataId;
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

  public MapWithHistory<String, Object> getFieldValueMap() {
    return _fieldValueMap;
  }

  private Comparable<?> _dataId;
  private DataResultProxyV2 _resultCache;

  private final DataType _dataType;
  private final MapWithHistory<String, Object> _fieldValueMap;
}
