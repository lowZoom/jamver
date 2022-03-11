package luj.game.server.internal.cluster.message.codec.encode;

import luj.game.server.api.plugin.JamverClusterProtoEncode;

final class ReturnImpl implements JamverClusterProtoEncode.Return, ClusterProtoEncoder.Result {

  @Override
  public JamverClusterProtoEncode.Return protoType(Class<?> val) {
    _protoType = val;
    return this;
  }

  @Override
  public JamverClusterProtoEncode.Return protoData(byte[] val) {
    _protoData = val;
    return this;
  }

  @Override
  public Class<?> protoType() {
    return _protoType;
  }

  @Override
  public byte[] protoData() {
    return _protoData;
  }

  private Class<?> _protoType;

  private byte[] _protoData;
}
