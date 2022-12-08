package luj.game.server.internal.network.proto.handle.invoke.defaultex;

import luj.game.server.api.net.ContextEx;

public class DefaultContextMaker {

  public DefaultContextMaker(String protoKey, Object protoObj) {
    _protoKey = protoKey;
    _protoObj = protoObj;
  }

  public ContextEx make() {
    var result = new ContextImpl();
    result._protoKey = _protoKey;
    result._protoObj = _protoObj;

    return result;
  }

  private final String _protoKey;

  private final Object _protoObj;
}
