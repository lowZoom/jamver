package luj.game.server.internal.cluster.message.codec.decode;

import luj.game.server.api.plugin.JamverClusterProtoDecode;

public class ClusterProtoDecoder {

  public ClusterProtoDecoder(JamverClusterProtoDecode decodePlugin, String protoTypeName,
      byte[] protoData) {
    _decodePlugin = decodePlugin;
    _protoTypeName = protoTypeName;
    _protoData = protoData;
  }

  public Object decode() throws Exception {
    Class<?> protoType = Class.forName(_protoTypeName);
    ContextImpl ctx = new ContextImpl(protoType, _protoData);

    return _decodePlugin.onDecode(ctx);
  }

  private final JamverClusterProtoDecode _decodePlugin;

  private final String _protoTypeName;
  private final byte[] _protoData;
}
