package luj.game.server.internal.cluster.proto.encode;

import luj.game.server.api.plugin.JamverClusterProtoEncode;

public enum ClusterProtoEncoder {
  GET;

  public interface Result {

    Class<?> protoType();

    byte[] protoData();
  }

  public Result encode(Object proto, JamverClusterProtoEncode encodePlugin) {
    ContextImpl ctx = new ContextImpl(proto);
    try {
      return (Result) encodePlugin.onEncode(ctx);

    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }
}
