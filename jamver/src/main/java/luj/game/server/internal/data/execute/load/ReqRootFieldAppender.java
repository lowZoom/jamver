package luj.game.server.internal.data.execute.load;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Function;
import luj.cache.api.request.CacheRequest;
import luj.game.server.internal.data.load.result.LoadResultProxy;

public enum ReqRootFieldAppender {
  GET;

  public CacheRequest.Node append(CacheRequest cacheReq, Function<Object, ?> fieldSpecifier,
      ResultFieldProxy fieldHolder, Comparable<?> dataId) {
    checkNotNull(dataId);
    ResultFieldProxy.Field loadField = fieldHolder.getField(fieldSpecifier);

    String fieldName = loadField.getName();
    Class<?> dataType = loadField.getDataType();

    return cacheReq.getRoot().addChild(dataType, dataId,
        (r, v) -> setResultField((LoadResultProxy) r, fieldName, v));
  }

  public CacheRequest.Node appendV2(CacheRequest cacheReq, Function<Object, ?> fieldSpecifier,
      ResultFieldProxy fieldHolder, Object nodeOp) {
    ResultFieldProxy.Field loadField = fieldHolder.getField(fieldSpecifier);

    String fieldName = loadField.getName();
    Class<?> dataType = loadField.getDataType();

    return cacheReq.getRoot().addChild(dataType,
        (r, v) -> setResultField((LoadResultProxy) r, fieldName, v), nodeOp);
  }

  private void setResultField(LoadResultProxy loadResult, String fieldName, Object value) {
    loadResult.getResultMap().put(fieldName, value);
  }
}
