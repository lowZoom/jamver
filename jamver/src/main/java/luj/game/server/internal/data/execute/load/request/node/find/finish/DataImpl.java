package luj.game.server.internal.data.execute.load.request.node.find.finish;

import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;

final class DataImpl implements LoadNodeOp.Data {

  DataImpl(Object result, Object aReturn) {
    _result = result;
    _return = aReturn;
  }

  @Override
  public Object getResultToSet() {
    return _result;
  }

  @Override
  public Object getReturnAsNextParent() {
    return _return;
  }

  static final DataImpl NULL = new DataImpl(null, null);

  private final Object _result;
  private final Object _return;
}
