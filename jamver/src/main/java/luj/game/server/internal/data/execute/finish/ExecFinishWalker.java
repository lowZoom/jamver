package luj.game.server.internal.data.execute.finish;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.cache.DataPresence;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultFactory;
import luj.game.server.internal.data.load.result.DataResultProxy;
import luj.game.server.internal.data.load.result.LoadResultProxy;

public class ExecFinishWalker implements RequestWalkListener {

  public ExecFinishWalker(CacheContainer dataCache, LoadResultProxy loadResult,
      List<DataTempProxy> loadLog, DataResultProxy.FieldHook fieldHook) {
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

  private Data idToList(DataTempProxy parentData, Function<Object, Object> idGetter,
      Class<?> dataType, DataResultProxy.FieldHook fieldHook) {
    List<Object> dataList = ImmutableList.copyOf(getJoinIds(parentData, idGetter)
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

  private Stream<Comparable<?>> getJoinIds(DataTempProxy parentData,
      Function<Object, Object> idGetter) {
    //TODO: 考虑不同形式的ID返回
    Object id = idGetter.apply(parentData.getInstance());
    return ((Collection<Comparable<?>>) id).stream();
  }

  private DataTempProxy getDataObj(Class<?> dataType, Comparable<?> dataId) {
    String dataKey = CacheKeyMaker.create(dataType, dataId).make();
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
    DataResultProxy result = new DataResultFactory(dataObj, fieldHook).create();
    _loadLog.add(dataObj);

    return result;
  }

  interface Data {

    Object getResult();

    Object getReturn();
  }

//  private static final Logger LOG = LoggerFactory.getLogger(FinishWalker.class);

  private final CacheContainer _dataCache;

  private final LoadResultProxy _loadResult;
  private final List<DataTempProxy> _loadLog;

  @Deprecated
  private final DataResultProxy.FieldHook _fieldHook;
}
