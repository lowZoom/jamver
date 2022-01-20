package luj.game.server.internal.luj.lujcluster.actor.cluster.send;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.stream.Collectors;
import luj.ava.spring.Internal;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.node.NodeNewMemberListener;
import luj.cluster.api.node.NodeType;
import luj.game.server.internal.cluster.proto.encode.ClusterProtoEncodeAndWrapper;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.cluster.receive.ClusterReceiveMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnClusterSend implements ClusterCommActor.Handler<ClusterSendMsg> {

  @Override
  public void onHandle(Context ctx) {
    ClusterCommActor self = ctx.getActorState(this);
    ClusterSendMsg msg = ctx.getMessage(this);

    String msgType = msg.getMessageType();
    Object msgObj = msg.getMessageObj();

    Collection<NodeNewMemberListener.Node> nodeList = getTargetList(self, msg);
    if (nodeList.isEmpty()) {
      LOG.warn("[game]消息没有对应的处理节点，将被丢弃：{}", msgType);
      return;
    }

    ClusterReceiveMsg resultMsg = ClusterProtoEncodeAndWrapper.GET
        .encodeAndWrap(msgObj, self.getProtoPlugin().getProtoEncode());

    for (NodeNewMemberListener.Node node : nodeList) {
      LOG.debug("------------发送jam {}", resultMsg.getMessageKey());
      node.sendMessage(CLUSTER_KEY, resultMsg);
    }
  }

  private Collection<NodeNewMemberListener.Node> getTargetList(ClusterCommActor self,
      ClusterSendMsg msg) {
    NodeNewMemberListener.Node overrideNode = msg.getTargetNode();
    if (overrideNode != null) {
      return ImmutableList.of(overrideNode);
    }

    Multimap<String, ActorMessageHandler.Node> dispatchMap = self.getDispatchMap();
    String msgType = msg.getMessageType();

    return dispatchMap.get(msgType).stream()
        .map(this::toNode)
        .collect(Collectors.toList());
  }

  private NodeNewMemberListener.Node toNode(ActorMessageHandler.Node node) {
    return new NodeNewMemberListener.Node() {
      @Override
      public void sendMessage(String msgKey, Object msg) {
        node.sendMessage(msgKey, msg);
      }
    };
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnClusterSend.class);

  private static final String CLUSTER_KEY = ClusterReceiveMsg.class.getName();
}
