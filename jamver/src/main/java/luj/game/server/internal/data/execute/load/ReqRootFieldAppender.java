package luj.game.server.internal.data.execute.load;

import java.util.function.Function;
import luj.cache.api.request.CacheRequest;

public class ReqRootFieldAppender {

  public ReqRootFieldAppender(CacheRequest cacheReq, Function<Object, ?> fieldSpecifier,
      ResultFieldProxy fieldHolder, Comparable<?> dataId) {
    _cacheReq = cacheReq;
    _fieldSpecifier = fieldSpecifier;
    _fieldHolder = fieldHolder;
    _dataId = dataId;
  }

  public CacheRequest.Node append() {
    ResultFieldProxy.Field loadField = _fieldHolder.getField(_fieldSpecifier);

    String fieldName = loadField.getName();
    Class<?> dataType = loadField.getDataType();

    return _cacheReq.getRoot().addChild(dataType, _dataId,
        (r, v) -> setResultField((ResultDataProxy) r, fieldName, v));
  }

  private void setResultField(ResultDataProxy loadResult, String fieldName, Object value) {
    loadResult.getResultMap().put(fieldName, value);
  }

  private final CacheRequest _cacheReq;

  private final Function<Object, ?> _fieldSpecifier;
  private final ResultFieldProxy _fieldHolder;

  private final Comparable<?> _dataId;
}
