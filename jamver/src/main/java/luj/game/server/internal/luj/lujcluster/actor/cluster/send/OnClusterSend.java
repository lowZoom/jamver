package luj.game.server.internal.luj.lujcluster.actor.cluster.send;

import com.google.common.collect.Multimap;
import java.util.Collection;
import luj.ava.spring.Internal;
import luj.cluster.api.actor.Tellable;
import luj.cluster.internal.node.message.receive.message.remote.NodeSendRemoteMsg;
import luj.game.server.internal.cluster.proto.encode.ClusterProtoEncoder;
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

    Multimap<String, Tellable> dispatchMap = self.getDispatchMap();
    String msgType = msg.getMessageType();

    Collection<Tellable> refList = dispatchMap.get(msgType);
    if (refList.isEmpty()) {
      LOG.warn("消息没有对应的处理节点，将被抛弃：{}", msgType);
      return;
    }

    ClusterProtoEncoder.Result encoded = new ClusterProtoEncoder(
        msg.getMessageObj(), self.getProtoPlugin().getProtoEncode()).encode();

    for (Tellable ref : refList) {
      ref.tell(new NodeSendRemoteMsg(ClusterReceiveMsg.class.getName(),
          new ClusterReceiveMsg(msgType, encoded.protoData())));
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnClusterSend.class);
}
