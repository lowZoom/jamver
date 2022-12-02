package luj.game.server.internal.luj.lujcluster.actor.network.register.dynamic;

import java.util.Map;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.network.proto.handle.collect.ProtoHandleMapCollector;
import luj.spring.anno.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnAddProtoHandle implements NetRootActor.Handler<AddProtoHandleMsg> {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);
    AddProtoHandleMsg msg = ctx.getMessage(this);

    Map<String, GameProtoHandler<?>> addMap =
        new ProtoHandleMapCollector(msg.protoHandler()).collect();

    LOG.debug("[game]新增GameProtoHandler，数量：{}", addMap.size());
    self.getProtoHandleMap().putAll(addMap);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnAddProtoHandle.class);
}
