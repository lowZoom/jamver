package luj.game.server.internal.data.execute.finish;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import luj.ava.spring.Internal;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.instance.DataInstanceCreator;
import luj.game.server.internal.data.instance.DataTempAdder;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class FinishWalker implements RequestWalkListener {

  @Deprecated
  FinishWalker() {
    this(null, null, null);
    LOG.warn("该形式准备移除");
  }

  FinishWalker(CacheContainer dataCache, ResultDataProxy loadResult,
      BiConsumer<DataTempProxy, String> fieldHook) {
    _dataCache = dataCache;
    _loadResult = loadResult;
    _fieldHook = fieldHook;
  }

  /**
   * @see CommandExecFinisher#finish
   */
  @Override
  public Object onWalk(Context ctx) {
    Data data = loadData(ctx, _dataCache, _fieldHook);
    ctx.getFieldSetter().accept(_loadResult, data.getResult());

    return data.getReturn();
  }

  private Data loadData(Context ctx, CacheContainer dataCache,
      BiConsumer<DataTempProxy, String> fieldHook) {
    Class<?> dataType = ctx.getDataType();
    Comparable<?> dataId = ctx.getDataId();
    if (dataId == null) {
      return idToList(ctx.getParentReturn(), ctx.getDataIdGetter(), dataType, fieldHook);
    }

    DataTempProxy dataProxy = getDataObj(dataType, dataId);
    if (dataProxy == null && DataTempProxy.GLOBAL.equals(dataId)) {
      return initGlobal(dataCache, dataType, fieldHook);
    }

//    LOG.debug("读取读取读取读取：{}, {}", dataType.getName(), dataId);
    return makeData(dataProxy, fieldHook);
  }

  private Data idToList(DataTempProxy parentData,
      Function<Object, Collection<Comparable<?>>> idGetter, Class<?> dataType,
      BiConsumer<DataTempProxy, String> fieldHook) {
    List<Object> dataList = ImmutableList.copyOf(idGetter.apply(parentData.getInstance()).stream()
//        .peek(id -> LOG.debug("数组读取读取读取读取：{}, {}", dataType.getName(), id))
        .map(id -> getDataObj(dataType, id))
        .filter(Objects::nonNull)
        .map(d -> new DataResultProxy(d, fieldHook).init())
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
    if (cacheItem == null || !cacheItem.isPresent()) {
      return null;
    }

    return cacheItem.getDataObj();
  }

  private Data initGlobal(CacheContainer dataCache, Class<?> dataType,
      BiConsumer<DataTempProxy, String> fieldHook) {
    DataTempProxy dataProxy = new DataInstanceCreator(dataType).create();

    dataProxy.getDataMap().put(DataTempProxy.ID, DataTempProxy.GLOBAL);
    new DataTempAdder(dataCache, dataType, dataProxy).add();

    return makeData(dataProxy, fieldHook);
  }

  private Data makeData(DataTempProxy dataProxy, BiConsumer<DataTempProxy, String> fieldHook) {
    return new Data() {
      @Override
      public Object getResult() {
        return (dataProxy == null) ? null :
            new DataResultProxy(dataProxy, fieldHook).init().getInstance();
      }

      @Override
      public Object getReturn() {
        return dataProxy;
      }
    };
  }

  interface Data {

    Object getResult();

    Object getReturn();
  }

  private static final Logger LOG = LoggerFactory.getLogger(FinishWalker.class);

  private final CacheContainer _dataCache;
  private final ResultDataProxy _loadResult;

  @Deprecated
  private final BiConsumer<DataTempProxy, String> _fieldHook;
}
