package luj.game.server.internal.cluster.proto.encode;

import luj.game.server.api.plugin.JamverClusterProtoEncode;

final class ContextImpl implements JamverClusterProtoEncode.Context {

  ContextImpl(Object proto) {
    _proto = proto;
  }

  @Override
  public Object getProtoObject() {
    return _proto;
  }

  @Override
  public JamverClusterProtoEncode.Return result() {
    return new ReturnImpl();
  }

  private final Object _proto;
}
