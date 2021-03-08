package luj.game.server.internal.luj.lujcluster.actor.network.send;

import io.netty.buffer.ByteBuf;
import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.api.plugin.JamverNetSendEncode;
import luj.game.server.internal.luj.lujcluster.actor.network.NetAllPlugin;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.network.send.encode.SendEncodeInvoker;
import luj.net.api.server.ConnectionAcceptInitializer;

@Internal
final class OnSendProto implements NetRootActor.Handler<SendProtoMsg> {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);
    SendProtoMsg msg = ctx.getMessage(this);

    NetAllPlugin allPlugin = self.getAllPlugin();
    JamverNetSendEncode encodePlugin = allPlugin.getSendEncode();
    ByteBuf dataBuf = new SendEncodeInvoker(encodePlugin, msg.getProto()).invoke();

    Map<Integer, ConnectionAcceptInitializer.Connection> connMap = self.getConnectionMap();
    ConnectionAcceptInitializer.Connection conn = connMap.get(msg.getConnectionId());

    conn.write(dataBuf);
    conn.flush();
  }
}
