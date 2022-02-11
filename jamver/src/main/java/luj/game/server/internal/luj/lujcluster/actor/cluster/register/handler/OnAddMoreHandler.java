package luj.game.server.internal.luj.lujcluster.actor.cluster.register.handler;

import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.cluster.handle.collect.ClusterHandleMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnAddMoreHandler implements ClusterCommActor.Handler<AddMoreHandlerMsg> {

  @Override
  public void onHandle(Context ctx) {
    ClusterCommActor self = ctx.getActorState(this);
    AddMoreHandlerMsg msg = ctx.getMessage(this);

    Map<String, ServerMessageHandler<?>> addMap =
        new ClusterHandleMapCollector(msg.getMessageHandler()).collect();

    LOG.debug("[game]新增MessageHandler，数量：{}", addMap.size());
    self.getHandlerMap().putAll(addMap);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnAddMoreHandler.class);
}
