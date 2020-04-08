package luj.game.server.internal.cluster.proto.decode;

import luj.game.server.api.plugin.JamverClusterProtoDecode;

final class ContextImpl implements JamverClusterProtoDecode.Context {

  ContextImpl(Class<?> protoType, byte[] protoData) {
    _protoType = protoType;
    _protoData = protoData;
  }

  @Override
  public Class<?> getProtoType() {
    return _protoType;
  }

  @Override
  public byte[] getProtoData() {
    return _protoData;
  }

  private final Class<?> _protoType;

  private final byte[] _protoData;
}
