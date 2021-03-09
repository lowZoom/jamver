package luj.game.server.internal.luj.lujcluster.actor.network.close;

import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.net.api.server.ConnectionAcceptInitializer;

@Internal
final class OnCloseConn implements NetRootActor.Handler<CloseConnMsg> {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);
    CloseConnMsg msg = ctx.getMessage(this);

    Integer connId = msg.getConnectionId();
    ConnectionAcceptInitializer.Connection conn = self.getConnectionMap().remove(connId);

    conn.close();
  }
}
