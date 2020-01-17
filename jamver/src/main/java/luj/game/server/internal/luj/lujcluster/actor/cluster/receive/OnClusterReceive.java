package luj.game.server.internal.luj.lujcluster.actor.cluster.receive;

import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommMsgHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnClusterReceive implements ClusterCommMsgHandler<ClusterReceiveMsg> {

  @Override
  public void onHandle(Context ctx) {
    ClusterCommActor actor = ctx.getActorState(this);
    ClusterReceiveMsg msg = ctx.getMessage(this);

    Map<String, ServerMessageHandler<?>> handlerMap = actor.getHandlerMap();
    String msgKey = msg.getMessageKey();

    ServerMessageHandler<?> handler = handlerMap.get(msgKey);
    if (handler == null) {
      LOG.warn("分布式消息没有处理器：{}", msgKey);
      return;
    }

    ServerImpl sender = new ServerImpl(ctx.getRemoteNode());
    HandleContextImpl handleCtx = new HandleContextImpl(msg.getMessage(), sender);
    try {
      handler.onHandle(handleCtx);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnClusterReceive.class);
}
