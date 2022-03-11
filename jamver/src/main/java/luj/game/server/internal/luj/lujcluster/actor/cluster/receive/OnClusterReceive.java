package luj.game.server.internal.luj.lujcluster.actor.cluster.receive;

import luj.ava.spring.Internal;
import luj.game.server.internal.cluster.message.codec.decode.ClusterProtoDecoder;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterProtoPlugin;
import luj.game.server.internal.luj.lujcluster.actor.cluster.handle.ClusterRecvHandleMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnClusterReceive implements ClusterCommActor.Handler<ClusterReceiveMsg> {

  @Override
  public void onHandle(Context ctx) throws Exception {
    ClusterCommActor self = ctx.getActorState(this);
    ClusterReceiveMsg msg = ctx.getMessage(this);

    String msgKey = msg.getMessageKey();
    LOG.debug("[game]收到分布式消息：{}", msgKey);

    ClusterProtoPlugin protoPlugin = self.getProtoPlugin();
    Object msgObj = new ClusterProtoDecoder(
        protoPlugin.getProtoDecode(), msgKey, msg.getMessageData()).decode();

    Ref selfRef = ctx.getActorRef();
    selfRef.tell(new ClusterRecvHandleMsg(msgKey, msgObj, ctx.getRemoteNode()));
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnClusterReceive.class);
}
