package luj.game.server.internal.luj.lujcluster.actor.network.register.dynamic;

import java.util.Collection;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.network.proto.handle.collect.ProtoHandleCollector;
import luj.spring.anno.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnAddProtoHandle implements NetRootActor.Handler<AddProtoHandleMsg> {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);
    AddProtoHandleMsg msg = ctx.getMessage(this);

    Collection<GameProtoHandler<?>> handlerList = msg.protoHandler();
    LOG.debug("[game]新增GameProtoHandler，数量：{}", handlerList.size());

    ProtoHandleCollector.Result addCollect = new ProtoHandleCollector(handlerList).collect();
    self.getProtoHandleMap().putAll(addCollect.handleMap());
    self.getDefaultHandler().addAll(addCollect.defaultHandler());
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnAddProtoHandle.class);
}
