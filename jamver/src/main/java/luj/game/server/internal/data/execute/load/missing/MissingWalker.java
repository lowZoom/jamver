package luj.game.server.internal.data.execute.load.missing;

import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheKeyMaker;

final class MissingWalker implements RequestWalkListener {

  MissingWalker(CacheContainer cache, List<LoadMissingCollector.Missing> out) {
    _cache = cache;
    _out = out;
  }

  @Override
  public Object onWalk(Context ctx) {
    Class<?> dataType = ctx.getDataType();
    Comparable<?> dataId = ctx.getDataId();

    //TODO: 处理 dataId==null 的情况

    String dataKey = new CacheKeyMaker(dataType, dataId).make();
    Object dataItem = _cache.get(dataKey);

    if (dataItem == null) {
      _out.add(new MissingImpl(dataType, dataId));
      return null;
    }

    return dataItem;
  }

  private final CacheContainer _cache;

  private final List<LoadMissingCollector.Missing> _out;
}
