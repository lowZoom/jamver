package luj.game.server.internal.data.execute.load;

import java.util.function.Function;
import luj.cache.api.request.CacheRequest;
import luj.game.server.internal.data.execute.load.result.ResultFieldProxy;
import luj.game.server.internal.data.load.result.LoadResultProxy;

public enum ReqChildFieldAppender {
  GET;

  public CacheRequest.Node append(CacheRequest.Node reqNode, Function<Object, ?> fieldSpecifier,
      ResultFieldProxy fieldHolder, Function<?, ?> idGetter) {
    ResultFieldProxy.Field loadField = fieldHolder.getField(fieldSpecifier);
    String fieldName = loadField.getName();

    return reqNode.addChild(idGetter, loadField.getDataType(),
        (r, v) -> setResultField((LoadResultProxy) r, fieldName, v));
  }

  public CacheRequest.Node appendV2(CacheRequest.Node reqNode, Function<Object, ?> fieldSpecifier,
      ResultFieldProxy fieldHolder, Object nodeOp) {
    ResultFieldProxy.Field loadField = fieldHolder.getField(fieldSpecifier);
    String fieldName = loadField.getName();

    return reqNode.addChild(loadField.getDataType(),
        (r, v) -> setResultField((LoadResultProxy) r, fieldName, v), nodeOp);
  }

  private void setResultField(LoadResultProxy loadResult, String fieldName, Object value) {
    loadResult.getResultMap().put(fieldName, value);
  }
}
