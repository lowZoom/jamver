package luj.game.server.internal.data.execute.finish;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import luj.ava.spring.Internal;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class FinishWalker implements RequestWalkListener {

  @Deprecated
  FinishWalker() {
    this(null, null, null, null);
    LOG.warn("该形式准备移除");
  }

  FinishWalker(CacheContainer dataCache, LoadResultProxy loadResult, List<DataResultProxy> loadLog,
      DataResultProxy.FieldHook fieldHook) {
    _dataCache = dataCache;
    _loadResult = loadResult;
    _loadLog = loadLog;
    _fieldHook = fieldHook;
  }

  /**
   * @see CommandExecFinisher#finish
   */
  @Override
  public Object onWalk(Context ctx) {
    Data data = loadData(ctx);
    ctx.getFieldSetter().accept(_loadResult, data.getResult());

    return data.getReturn();
  }

  private Data loadData(Context ctx) {
    Class<?> dataType = ctx.getDataType();
    Comparable<?> dataId = ctx.getDataId();
    if (dataId == null) {
      return idToList(ctx.getParentReturn(), ctx.getDataIdGetter(), dataType, _fieldHook);
    }

    DataTempProxy dataObj = getDataObj(dataType, dataId);
//    LOG.debug("读取读取读取读取：{}, {}", dataType.getName(), dataId);

    return makeData(dataObj, _fieldHook);
  }

  private Data idToList(DataTempProxy parentData,
      Function<Object, Collection<Comparable<?>>> idGetter, Class<?> dataType,
      DataResultProxy.FieldHook fieldHook) {
    List<Object> dataList = ImmutableList.copyOf(idGetter.apply(parentData.getInstance()).stream()
//        .peek(id -> LOG.debug("数组读取读取读取读取：{}, {}", dataType.getName(), id))
        .map(id -> getDataObj(dataType, id))
        .filter(Objects::nonNull)
        .map(d -> createResultAndLog(d, fieldHook))
        .map(DataResultProxy::getInstance)
        .iterator());

    return new Data() {
      @Override
      public Object getResult() {
        return dataList;
      }

      @Override
      public Object getReturn() {
        return dataList;
      }
    };
  }

  private DataTempProxy getDataObj(Class<?> dataType, Comparable<?> dataId) {
    String dataKey = new CacheKeyMaker(dataType, dataId).make();
//    LOG.debug("读取读取读取读取：{}", dataKey);

    CacheItem cacheItem = _dataCache.get(dataKey);
    checkNotNull(cacheItem, dataKey);

    DataPresence presence = cacheItem.getPresence();
    DataTempProxy dataObj = cacheItem.getDataObj();

    if (presence == DataPresence.ABSENT) {
      checkState(dataObj == null, dataKey);
      return null;
    }

    checkState(presence == DataPresence.PRESENT, dataKey);
    return checkNotNull(dataObj, dataKey);
  }

  private Data makeData(DataTempProxy dataProxy, DataResultProxy.FieldHook fieldHook) {
    return new Data() {
      @Override
      public Object getResult() {
        return (dataProxy == null) ? null : createResultAndLog(dataProxy, fieldHook).getInstance();
      }

      @Override
      public Object getReturn() {
        return dataProxy;
      }
    };
  }

  private DataResultProxy createResultAndLog(DataTempProxy dataObj,
      DataResultProxy.FieldHook fieldHook) {
    DataResultProxy result = new DataResultProxy(dataObj, fieldHook).init();
    _loadLog.add(result);

    return result;
  }

  interface Data {

    Object getResult();

    Object getReturn();
  }

  private static final Logger LOG = LoggerFactory.getLogger(FinishWalker.class);

  private final CacheContainer _dataCache;

  private final LoadResultProxy _loadResult;
  private final List<DataResultProxy> _loadLog;

  @Deprecated
  private final DataResultProxy.FieldHook _fieldHook;
}
