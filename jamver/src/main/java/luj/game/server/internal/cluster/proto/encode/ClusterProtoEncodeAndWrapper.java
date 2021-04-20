package luj.game.server.internal.cluster.proto.encode;

import luj.game.server.api.plugin.JamverClusterProtoEncode;
import luj.game.server.internal.luj.lujcluster.actor.cluster.receive.ClusterReceiveMsg;

public enum ClusterProtoEncodeAndWrapper {
  GET;

  public ClusterReceiveMsg encodeAndWrap(Object proto, JamverClusterProtoEncode encodePlugin) {
    ClusterProtoEncoder.Result encoded = ClusterProtoEncoder.GET.encode(proto, encodePlugin);
    String typeName = encoded.protoType().getName();

    return new ClusterReceiveMsg(typeName, encoded.protoData());
  }
}
