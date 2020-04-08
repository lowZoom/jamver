package luj.game.server.internal.cluster.handle;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.plugin.JamverClusterProtoEncode;
import luj.game.server.internal.cluster.proto.encode.ClusterProtoEncoder;
import luj.game.server.internal.luj.lujcluster.actor.cluster.receive.ClusterReceiveMsg;

final class ServerImpl implements ServerMessageHandler.Server {

  ServerImpl(ActorMessageHandler.Node remoteNode, JamverClusterProtoEncode encodePlugin) {
    _remoteNode = remoteNode;
    _encodePlugin = encodePlugin;
  }

  @Override
  public void sendMessage(Object msg) {
    ClusterProtoEncoder.Result encoded = new ClusterProtoEncoder(msg, _encodePlugin).encode();
    String typeName = encoded.protoType().getName();

    ClusterReceiveMsg gameMsg = new ClusterReceiveMsg(typeName, encoded.protoData());
    _remoteNode.sendMessage(ClusterReceiveMsg.class.getName(), gameMsg);
  }

  @Override
  public String toString() {
    return _remoteNode.toString();
  }

  private final ActorMessageHandler.Node _remoteNode;

  private final JamverClusterProtoEncode _encodePlugin;
}
