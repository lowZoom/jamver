package luj.game.server.internal.data.id.init;

import luj.game.server.api.plugin.JamverDataIdInit;

final class InitReturnImpl implements JamverDataIdInit.Return {

  @Override
  public JamverDataIdInit.Return state(Object state) {
    _state = state;
    return this;
  }

  @Override
  public JamverDataIdInit.Return idField(String field) {
    _idField = field;
    return this;
  }

  @Override
  public JamverDataIdInit.Return globalId(Comparable<?> id) {
    _globalId = id;
    return this;
  }

  Object _state;

  String _idField;
  Comparable<?> _globalId;
}
