package luj.game.server.internal.data.execute.load;

import java.util.function.Function;
import luj.cache.api.request.CacheRequest;
import luj.game.server.internal.data.load.result.LoadResultProxy;

public enum ReqChildTransientAppender {
  GET;

  public CacheRequest.Node append(CacheRequest.Node reqNode, Class<?> fieldType,
      Function<?, ?> idGetter) {
    return reqNode.addChild(idGetter, fieldType, this::setResultField);
  }

  public CacheRequest.Node appendV2(CacheRequest.Node reqNode, Class<?> fieldType, Object nodeOp) {
    return reqNode.addChild(fieldType, this::setResultField, nodeOp);
  }

  private void setResultField(LoadResultProxy loadResult, Object value) {
    //    LOG.debug("临时变量：{}", _dataType.getName());
  }
}
