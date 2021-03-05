package luj.game.server.internal.luj.lujcluster.actor.network.receive;

import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.api.plugin.JamverNetReceivePacket;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.network.proto.handle.invoke.ProtoHandleInvoker;
import luj.game.server.internal.network.receive.packet.JamPacketReceiveInvoker;

@Internal
final class OnReceivePacket implements NetRootActor.Handler<ReceivePacketMsg> {

  @Override
  public void onHandle(Context ctx) throws Exception {
    NetRootActor self = ctx.getActorState(this);
    ReceivePacketMsg msg = ctx.getMessage(this);

    JamverNetReceivePacket<?> packetPlugin = self.getReceivePlugin().getReceivePacket();
    JamPacketReceiveInvoker.Result result =
        JamPacketReceiveInvoker.GET.invoke(packetPlugin, msg.getPacket());

    Map<Class<?>, GameProtoHandler<?>> handlerMap = self.getHandlerMap();
    GameProtoHandler<?> handler = handlerMap.get(result.protoType());

    ProtoHandleInvoker.GET.invoke(handler, result.protoInstance(), msg.getConnectionId(), self);
  }

//  private static final Logger LOG = LoggerFactory.getLogger(OnReceivePacket.class);
}
