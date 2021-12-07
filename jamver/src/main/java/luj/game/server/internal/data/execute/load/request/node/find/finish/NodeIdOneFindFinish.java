package luj.game.server.internal.data.execute.load.request.node.find.finish;

import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public enum NodeIdOneFindFinish {
  GET;

  public LoadNodeOp.Data find(CacheContainer dataCache, Class<?> dataType, Comparable<?> dataId,
      DataResultProxyV2.FieldHook fieldHook, List<DataEntity> loadLog) {
    DataEntity dataObj = getDataObjAndLock(dataCache, dataType, dataId);
//    LOG.debug("读取读取读取读取：{}, {}", dataType.getName(), dataId);

    return makeData(dataObj, dataType, fieldHook, loadLog);
  }

  DataEntity getDataObjAndLock(CacheContainer dataCache, Class<?> dataType, Comparable<?> dataId) {
    return DataObjGetAndLocker.GET.getAndLock(dataCache, dataType, dataId);
  }

  DataResultProxyV2 createResultAndLog(DataEntity dataObj, Class<?> dataType,
      DataResultProxyV2.FieldHook fieldHook, List<DataEntity> loadLog) {
    DataResultProxyV2 result = DataResultProxyV2.create(dataObj, dataType, fieldHook);
    dataObj.setResultCache(result);
    loadLog.add(dataObj);
    return result;
  }

  private LoadNodeOp.Data makeData(DataEntity dataObj, Class<?> dataType,
      DataResultProxyV2.FieldHook fieldHook, List<DataEntity> loadLog) {
    if (dataObj == null) {
      return DataImpl.NULL;
    }

    Object result = createResultAndLog(dataObj, dataType, fieldHook, loadLog).getInstance();
    return new DataImpl(result, dataObj);
  }
}
