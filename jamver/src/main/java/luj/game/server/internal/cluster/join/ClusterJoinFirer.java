package luj.game.server.internal.cluster.join;

import java.util.List;
import luj.cluster.api.node.NodeNewMemberListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.plugin.JamverClusterProtoEncode;

public class ClusterJoinFirer {

  public ClusterJoinFirer(List<ServerJoinListener> listenerList,
      NodeNewMemberListener.Node remoteNode, JamverClusterProtoEncode encodePlugin) {
    _listenerList = listenerList;
    _remoteNode = remoteNode;
    _encodePlugin = encodePlugin;
  }

  public void fire() throws Exception {
    ServerImpl server = new ServerImpl(_remoteNode, _encodePlugin);
    JoinContextImpl ctx = new JoinContextImpl(server);

    for (ServerJoinListener listener : _listenerList) {
      listener.onHandle(ctx);
    }
  }

  private final List<ServerJoinListener> _listenerList;

  private final NodeNewMemberListener.Node _remoteNode;
  private final JamverClusterProtoEncode _encodePlugin;
}
