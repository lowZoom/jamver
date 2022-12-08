package luj.game.server.internal.luj.lujcluster.actor.network.receive;

import java.util.List;
import luj.game.server.api.net.ContextEx;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.network.proto.handle.collect.ProtoHandleMap;
import luj.game.server.internal.network.proto.handle.invoke.ProtoHandleInvoker;
import luj.game.server.internal.network.proto.handle.invoke.defaultex.DefaultContextMaker;
import luj.spring.anno.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnReceiveProto implements NetRootActor.Handler<ReceiveProtoMsg> {

  @Override
  public void onHandle(Context ctx) throws Exception {
    NetRootActor self = ctx.getActorState(this);
    ReceiveProtoMsg msg = ctx.getMessage(this);

    ProtoHandleMap handleMap = self.getProtoHandleMap();
    String protoKey = msg.protoId();
    GameProtoHandler<?> handler = handleMap.getHandler(protoKey);

    ProtoHandleInvoker invoker = ProtoHandleInvoker.GET;
    Object receiveParam = msg.param();
    Object protoObj = msg.protoObj();

    if (handler != null) {
      invoker.invoke(handler, protoObj, receiveParam, self);
      return;
    }

    List<GameProtoHandler.Default> defaultHandle = self.getDefaultHandler();
    if (defaultHandle.isEmpty()) {
      LOG.warn("[game]协议无对应处理器，忽略：{}", protoKey);
      return;
    }

    ContextEx defaultCtx = new DefaultContextMaker(protoKey, protoObj).make();
    for (GameProtoHandler.Default h : defaultHandle) {
      invoker.invoke(h, defaultCtx, receiveParam, self);
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnReceiveProto.class);
}
