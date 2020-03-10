package luj.game.server.internal.data.execute.load;

import java.lang.reflect.Method;
import java.util.function.Function;
import luj.cache.api.request.CacheRequest;

public class LoadRequestAppender {

  public LoadRequestAppender(CacheRequest cacheReq, Function<Object, ?> fieldSpecifier,
      ResultFieldProxy fieldHolder, Comparable<?> dataId) {
    _cacheReq = cacheReq;
    _fieldSpecifier = fieldSpecifier;
    _fieldHolder = fieldHolder;
    _dataId = dataId;
  }

  public void append() {
    Method field = _fieldHolder.getField(_fieldSpecifier);

    String fieldName = field.getName();
    Class<?> fieldType = field.getReturnType();

    _cacheReq.addNode(fieldType, _dataId,
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
