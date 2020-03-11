package luj.game.server.internal.luj.lujcache;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import luj.ava.spring.Internal;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.execute.load.DataLoadRequestMaker;
import luj.game.server.internal.data.instance.DataInstanceCreator;
import luj.game.server.internal.data.instance.DataTempAdder;
import luj.game.server.internal.data.instance.DataTempProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnWalkReq implements RequestWalkListener {

  /**
   * @see DataLoadRequestMaker#make
   */
  @Override
  public Object onWalk(Context ctx) {
    DataLoadRequestMaker.Context param = ctx.getRequestParam();

    //FIXME: 改为到数据中心读取
    Data data = loadData(ctx, param.getDataCache());
    ctx.getFieldSetter().accept(param.getLoadResult(), data.getResult());

    return data.getReturn();
  }

  private Data loadData(Context ctx, CacheContainer dataCache) {
    Comparable<?> dataId = ctx.getDataId();
    Class<?> dataType = ctx.getDataType();
    if (dataId == null) {
      return idToList(ctx.getParentReturn(), ctx.getDataIdGetter(), dataType, dataCache);
    }

    DataTempProxy dataProxy = dataCache.get(dataKey(dataType, dataId));
    if (dataProxy == null && DataTempProxy.GLOBAL.equals(dataId)) {
      return initGlobal(dataCache, dataType);
    }

    LOG.debug("读取读取读取读取：{}, {}", dataType.getName(), dataId);
    return makeData(dataProxy);
  }

  private Data idToList(DataTempProxy parentData,
      Function<Object, Collection<Comparable<?>>> idGetter, Class<?> dataType,
      CacheContainer dataCache) {
    List<DataTempProxy> dataList = idGetter.apply(parentData.getInstance()).stream()
        .peek(id -> LOG.debug("数组读取读取读取读取：{}, {}", dataType.getName(), id))
        .map(id -> (DataTempProxy) dataCache.get(dataKey(dataType, id)))
        .collect(Collectors.toList());

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

  private Data initGlobal(CacheContainer dataCache, Class<?> dataType) {
    DataTempProxy dataProxy = new DataInstanceCreator(dataType).create();
    new DataTempAdder(dataCache, dataType, dataProxy).add();
    return makeData(dataProxy);
  }

  private String dataKey(Class<?> dataType, Comparable<?> dataId) {
    return dataType.getName() + "#" + dataId;
  }

  private Data makeData(DataTempProxy dataProxy) {
    return new Data() {
      @Override
      public Object getResult() {
        return (dataProxy == null) ? null : dataProxy.getInstance();
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

  private static final Logger LOG = LoggerFactory.getLogger(OnWalkReq.class);
}
