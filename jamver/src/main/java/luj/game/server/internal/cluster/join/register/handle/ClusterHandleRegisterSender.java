package luj.game.server.internal.cluster.join.register.handle;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import luj.ava.reflect.type.TypeX;
import luj.cluster.api.node.member.NodeNewMemberListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.cluster.register.handle.JamRegisterHandleMsg;

public class ClusterHandleRegisterSender {

  public ClusterHandleRegisterSender(List<ServerMessageHandler<?>> clusterMessageList,
      NodeNewMemberListener.Node remoteNode) {
    _clusterMessageList = clusterMessageList;
    _remoteNode = remoteNode;
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.cluster.register.handle.OnRegisterHandle#onHandle
   */
  public void send() {
    Set<String> handleSet = _clusterMessageList.stream()
        .map(h -> TypeX.ofInstance(h).getSupertype(ServerMessageHandler.class))
        .map(t -> t.getTypeParam(0).asClass())
        .map(Class::getName)
        .collect(Collectors.toSet());

    JamRegisterHandleMsg msg = new JamRegisterHandleMsg(handleSet);
    _remoteNode.sendMessage(JamRegisterHandleMsg.class.getName(), msg);
  }

  private final List<ServerMessageHandler<?>> _clusterMessageList;

  private final NodeNewMemberListener.Node _remoteNode;
}
