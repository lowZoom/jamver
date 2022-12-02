package luj.game.server.internal.luj.lujcluster.actor.network.receive;

import java.util.Map;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.network.proto.handle.invoke.ProtoHandleInvoker;
import luj.spring.anno.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnReceiveProto implements NetRootActor.Handler<ReceiveProtoMsg> {

  @Override
  public void onHandle(Context ctx) throws Exception {
    NetRootActor self = ctx.getActorState(this);
    ReceiveProtoMsg msg = ctx.getMessage(this);

    Map<String, GameProtoHandler<?>> handleMap = self.getProtoHandleMap();
    String protoId = msg.protoId();

    GameProtoHandler<?> handler = handleMap.get(protoId);
    if (handler == null) {
      LOG.warn("[game]协议无对应处理器，忽略：{}", protoId);
      return;
    }

    ProtoHandleInvoker.GET.invoke(handler, msg.protoObj(), self);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnReceiveProto.class);
}
