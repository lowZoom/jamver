package luj.game.server.internal.luj.lujcluster.actor.cluster.send;

import com.google.common.collect.Multimap;
import java.util.Collection;
import luj.ava.spring.Internal;
import luj.cluster.api.actor.ActorMessageHandler;
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

    Multimap<String, ActorMessageHandler.Node> dispatchMap = self.getDispatchMap();
    String msgType = msg.getMessageType();

    Collection<ActorMessageHandler.Node> nodeList = dispatchMap.get(msgType);
    if (nodeList.isEmpty()) {
      LOG.warn("消息没有对应的处理节点，将被丢弃：{}", msgType);
      return;
    }

    ClusterReceiveMsg resultMsg = ClusterProtoEncodeAndWrapper.GET
        .encodeAndWrap(msg.getMessageObj(), self.getProtoPlugin().getProtoEncode());

    for (ActorMessageHandler.Node node : nodeList) {
      node.sendMessage(ClusterReceiveMsg.class.getName(), resultMsg);
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnClusterSend.class);
}
