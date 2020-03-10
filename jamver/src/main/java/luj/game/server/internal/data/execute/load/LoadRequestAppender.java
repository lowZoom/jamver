package luj.game.server.internal.data.execute.load;

import java.lang.reflect.Method;
import java.util.function.Function;
import luj.cache.api.request.CacheRequest;

public class LoadRequestAppender {

  public LoadRequestAppender(CacheRequest cacheReq,
      ResultDataProxy loadResult, Function<Object, ?> fieldSpecifier,
      ResultFieldProxy fieldHolder, Comparable<?> dataId) {
    _cacheReq = cacheReq;
    _loadResult = loadResult;
    _fieldSpecifier = fieldSpecifier;
    _fieldHolder = fieldHolder;
    _dataId = dataId;
  }

  public void append() {
    Method field = _fieldHolder.getField(_fieldSpecifier);

    String fieldName = field.getName();
    Class<?> fieldType = field.getReturnType();

    System.out.println("bbbbbbbbbbbbbbbb: " + fieldName);
    _cacheReq.addNode(fieldType, _dataId, (r, v) -> setResultField(fieldName, v));
  }

  private void setResultField(String fieldName, Object value) {
    _loadResult.getResultMap().put(fieldName, value);
  }

  private final CacheRequest _cacheReq;
  private final ResultDataProxy _loadResult;

  private final Function<Object, ?> _fieldSpecifier;
  private final ResultFieldProxy _fieldHolder;

  private final Comparable<?> _dataId;
}
