package luj.game.server.internal.data.execute.load;

import java.lang.reflect.Method;
import java.util.function.Function;
import luj.cache.api.request.CacheRequest;

public class ReqChildFieldAppender {

  public ReqChildFieldAppender(CacheRequest.Node reqNode, Function<Object, ?> fieldSpecifier,
      ResultFieldProxy fieldHolder, Function<?, ?> idGetter) {
    _reqNode = reqNode;
    _fieldSpecifier = fieldSpecifier;
    _fieldHolder = fieldHolder;
    _idGetter = idGetter;
  }

  public CacheRequest.Node append() {
    Method loadField = _fieldHolder.getField(_fieldSpecifier);
    String fieldName = loadField.getName();

    _reqNode.addChild(_idGetter, (r, v) -> setResultField((ResultDataProxy) r, fieldName, v));
    return null;
  }

  private void setResultField(ResultDataProxy loadResult, String fieldName, Object value) {
    loadResult.getResultMap().put(fieldName, value);
  }

  private final CacheRequest.Node _reqNode;

  private final Function<Object, ?> _fieldSpecifier;
  private final ResultFieldProxy _fieldHolder;

  private final Function<?, ?> _idGetter;
}
