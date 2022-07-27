package luj.game.server.internal.cluster.join;

import java.util.Set;
import java.util.function.BiFunction;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.cluster.api.node.member.NodeNewMemberListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.service.param.CommandParamMaker;
import luj.game.server.internal.luj.lujcluster.actor.cluster.send.ClusterSendMsg;

final class ServerImpl implements ServerJoinListener.Server {

  @Override
  public String name() {
    return _remoteNode.getName();
  }

  @Override
  public Set<String> tags() {
    return _remoteNode.getTags();
  }

  @Override
  public void sendMessage(Object msg) {
    String msgPath = msg.getClass().getName();
    _clusterRef.tell(new ClusterSendMsg(msgPath, msg, _remoteNode));
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> void sendMessage(Class<T> msgType,
      BiFunction<CommandService.Param, T, CommandService.Param> msgValue) {
    Object msgObj = new CommandParamMaker(msgType,
        (b, p) -> msgValue.apply(b, (T) p), _lujbean).make();

    sendMessage(msgObj);
  }

  @Override
  public String toString() {
    return _remoteNode.toString();
  }

  NodeNewMemberListener.NodeRemote _remoteNode;

  Tellable _clusterRef;
  BeanContext _lujbean;
}
