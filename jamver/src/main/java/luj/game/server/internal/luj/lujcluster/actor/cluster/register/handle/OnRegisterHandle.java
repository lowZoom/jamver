package luj.game.server.internal.luj.lujcluster.actor.cluster.register.handle;

import java.util.Set;
import luj.ava.spring.Internal;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnRegisterHandle implements ClusterCommActor.Handler<JamRegisterHandleMsg> {

  @Override
  public void onHandle(Context ctx) {
    ClusterCommActor self = ctx.getActorState(this);
    JamRegisterHandleMsg msg = ctx.getMessage(this);

    Set<String> canHandleSet = msg.getMessageCanHandle();
    LOG.debug("远程节点注册消息处理，数量：{}", canHandleSet.size());

    ActorMessageHandler.Node remoteNode = ctx.getRemoteNode();
    for (String msgType : canHandleSet) {
      self.getDispatchMap().put(msgType, remoteNode);
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnRegisterHandle.class);
}
