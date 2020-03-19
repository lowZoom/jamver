package luj.game.server.internal.luj.lujcache;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import luj.ava.spring.Internal;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.execute.load.DataLoadRequestMaker;
import luj.game.server.internal.data.instance.DataInstanceCreator;
import luj.game.server.internal.data.instance.DataKeyMaker;
import luj.game.server.internal.data.instance.DataTempAdder;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultProxy;

@Internal
final class OnWalkReq implements RequestWalkListener {

  /**
   * @see DataLoadRequestMaker#make
   */
  @Override
  public Object onWalk(Context ctx) {
    DataLoadRequestMaker.Context param = ctx.getRequestParam();

    //TODO: 改为向数据中心发起读取请求
    Data data = loadData(ctx, param.getDataCache(), param.getFieldHook());
    ctx.getFieldSetter().accept(param.getLoadResult(), data.getResult());

    return data.getReturn();
  }

  private Data loadData(Context ctx, CacheContainer dataCache,
      BiConsumer<DataTempProxy, String> fieldHook) {
    Class<?> dataType = ctx.getDataType();
    Comparable<?> dataId = ctx.getDataId();
    if (dataId == null) {
      return idToList(ctx.getParentReturn(), ctx.getDataIdGetter(), dataType, dataCache, fieldHook);
    }

    DataTempProxy dataProxy = dataCache.get(dataKey(dataType, dataId));
    if (dataProxy == null && DataTempProxy.GLOBAL.equals(dataId)) {
      return initGlobal(dataCache, dataType, fieldHook);
    }

//    LOG.debug("读取读取读取读取：{}, {}", dataType.getName(), dataId);
    return makeData(dataProxy, fieldHook);
  }

  private Data idToList(DataTempProxy parentData,
      Function<Object, Collection<Comparable<?>>> idGetter, Class<?> dataType,
      CacheContainer dataCache, BiConsumer<DataTempProxy, String> fieldHook) {
    List<Object> dataList = ImmutableList.copyOf(idGetter.apply(parentData.getInstance()).stream()
//        .peek(id -> LOG.debug("数组读取读取读取读取：{}, {}", dataType.getName(), id))
        .map(id -> (DataTempProxy) dataCache.get(dataKey(dataType, id)))
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

  private Data initGlobal(CacheContainer dataCache, Class<?> dataType,
      BiConsumer<DataTempProxy, String> fieldHook) {
    DataTempProxy dataProxy = new DataInstanceCreator(dataType).create();

    dataProxy.getDataMap().put(DataTempProxy.ID, DataTempProxy.GLOBAL);
    new DataTempAdder(dataCache, dataType, dataProxy).add();

    return makeData(dataProxy, fieldHook);
  }

  private String dataKey(Class<?> dataType, Comparable<?> dataId) {
    return new DataKeyMaker(dataType, dataId).make();
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

//  private static final Logger LOG = LoggerFactory.getLogger(OnWalkReq.class);
}
