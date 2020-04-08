package luj.game.server.internal.cluster.proto.encode;

import luj.game.server.api.plugin.JamverClusterProtoEncode;

public class ClusterProtoEncoder {

  public interface Result {

    Class<?> protoType();

    byte[] protoData();
  }

  public ClusterProtoEncoder(Object proto, JamverClusterProtoEncode encodePlugin) {
    _proto = proto;
    _encodePlugin = encodePlugin;
  }

  public Result encode() {
    ContextImpl ctx = new ContextImpl(_proto);
    try {
      return (Result) _encodePlugin.onEncode(ctx);

    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private final Object _proto;

  private final JamverClusterProtoEncode _encodePlugin;
}
