package luj.game.server.internal.luj.lujcluster.actor.cluster.receive;

import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.cluster.handle.ServmsgHandleInvoker;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnClusterReceive implements ClusterCommActor.Handler<ClusterReceiveMsg> {

  @Override
  public void onHandle(Context ctx) {
    ClusterCommActor actor = ctx.getActorState(this);
    ClusterReceiveMsg msg = ctx.getMessage(this);

    Map<String, ServerMessageHandler<?>> handlerMap = actor.getHandlerMap();
    String msgKey = msg.getMessageKey();

    LOG.debug("[game]收到分布式消息：{}", msgKey);

    new ServmsgHandleInvoker(ctx.getRemoteNode(), actor.getDataRef(), handlerMap, msgKey,
        msg.getMessage()).invoke();
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnClusterReceive.class);
}
