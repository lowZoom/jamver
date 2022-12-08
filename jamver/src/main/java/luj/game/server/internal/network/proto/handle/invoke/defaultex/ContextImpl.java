package luj.game.server.internal.network.proto.handle.invoke.defaultex;

import luj.game.server.api.net.ContextEx;

final class ContextImpl implements ContextEx {

  @Override
  public String protoKey() {
    return _protoKey;
  }

  @Override
  public Object protoObject() {
    return _protoObj;
  }

  String _protoKey;

  Object _protoObj;
}
