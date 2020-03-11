package luj.game.server.internal.data.execute.load;

import java.lang.reflect.Method;
import java.util.function.Function;
import luj.cache.api.request.CacheRequest;

public class LoadRequestFieldAppender {

  public LoadRequestFieldAppender(CacheRequest.Node reqNode, Function<Object, ?> fieldSpecifier,
      ResultFieldProxy fieldHolder, Comparable<?> dataId) {
    _reqNode = reqNode;
    _fieldSpecifier = fieldSpecifier;
    _fieldHolder = fieldHolder;
    _dataId = dataId;
  }

  public CacheRequest.Node append() {
    Method loadField = _fieldHolder.getField(_fieldSpecifier);

    String fieldName = loadField.getName();
    Class<?> fieldType = loadField.getReturnType();

    return _reqNode.addChild(fieldType, _dataId,
        (r, v) -> setResultField((ResultDataProxy) r, fieldName, v));
  }

  private void setResultField(ResultDataProxy loadResult, String fieldName, Object value) {
    loadResult.getResultMap().put(fieldName, value);
  }

  private final CacheRequest.Node _reqNode;

  private final Function<Object, ?> _fieldSpecifier;
  private final ResultFieldProxy _fieldHolder;

  private final Comparable<?> _dataId;
}
