package luj.game.server.internal.data.execute.load.request.node.find.finish;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;
import luj.game.server.internal.data.execute.load.request.node.find.finish.lock.DataObjGetAndLocker;
import luj.game.server.internal.data.execute.load.request.node.find.finish.lock.DataPair;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

public enum NodeIdOneFindFinish {
  GET;

  public LoadNodeOp.Data find(CacheContainer dataCache, Class<?> dataType, Comparable<?> dataId,
      DataResultProxyV2.FieldHook fieldHook, Map<String, DataPair> loadLog) {
    checkNotNull(dataId);

    DataPair data = getDataObjAndLock(dataCache, dataType, dataId, loadLog);
//    LOG.debug("读取读取读取读取：{}, {}", dataType.getName(), dataId);

    return makeData(data, dataType, fieldHook, loadLog);
  }

  DataResultProxyV2 getParentResult(RequestWalkListener.Context ctx) {
    DataPair parentReturn = ctx.getParentReturn();
    return parentReturn.getData().getResultCache();
  }

  DataPair getDataObjAndLock(CacheContainer dataCache, Class<?> dataType, Comparable<?> dataId,
      Map<String, DataPair> loadLog) {
    return DataObjGetAndLocker.GET.getAndLock(dataCache, dataType, dataId, loadLog);
  }

  DataResultProxyV2 createResultAndLog(DataPair data, Class<?> dataType,
      DataResultProxyV2.FieldHook fieldHook, Map<String, DataPair> loadLog) {
    DataEntity dataObj = data.getData();
    DataResultProxyV2 result = DataResultProxyV2.create(dataObj, dataType, fieldHook);
    dataObj.setResultCache(result);

    loadLog.put(data.getKey(), data);
    return result;
  }

  private LoadNodeOp.Data makeData(DataPair data, Class<?> dataType,
      DataResultProxyV2.FieldHook fieldHook, Map<String, DataPair> loadLog) {
    if (data == null) {
      return DataImpl.NULL;
    }

    Object result = createResultAndLog(data, dataType, fieldHook, loadLog).getInstance();
    return new DataImpl(result, data);
  }
}
