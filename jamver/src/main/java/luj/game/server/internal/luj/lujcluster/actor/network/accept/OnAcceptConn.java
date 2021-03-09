package luj.game.server.internal.luj.lujcluster.actor.network.accept;

import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.network.accept.AcceptHandleInvoker;
import luj.net.api.server.ConnectionAcceptInitializer;

@Internal
final class OnAcceptConn implements NetRootActor.Handler<AcceptConnMsg> {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);
    AcceptConnMsg msg = ctx.getMessage(this);

    Integer connId = msg.getConnectionId();
    ConnectionAcceptInitializer.Connection conn = msg.getConnection();
    self.getConnectionMap().put(connId, conn);

    AcceptHandleInvoker.GET.invoke(self, ctx.getActorRef(), connId, conn, msg.getBindAddr());
  }
}
