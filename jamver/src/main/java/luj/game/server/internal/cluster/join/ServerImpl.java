package luj.game.server.internal.cluster.join;

import luj.cluster.api.node.NodeNewMemberListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.plugin.JamverClusterProtoEncode;
import luj.game.server.internal.cluster.proto.encode.ClusterProtoEncodeAndWrapper;
import luj.game.server.internal.luj.lujcluster.actor.cluster.receive.ClusterReceiveMsg;

final class ServerImpl implements ServerJoinListener.Server {

  ServerImpl(NodeNewMemberListener.Node remoteNode, JamverClusterProtoEncode encodePlugin) {
    _remoteNode = remoteNode;
    _encodePlugin = encodePlugin;
  }

  @Override
  public void sendMessage(Object msg) {
    ClusterReceiveMsg gameMsg = ClusterProtoEncodeAndWrapper.GET.encodeAndWrap(msg, _encodePlugin);
    _remoteNode.sendMessage(ClusterReceiveMsg.class.getName(), gameMsg);
  }

  private final NodeNewMemberListener.Node _remoteNode;

  private final JamverClusterProtoEncode _encodePlugin;
}
