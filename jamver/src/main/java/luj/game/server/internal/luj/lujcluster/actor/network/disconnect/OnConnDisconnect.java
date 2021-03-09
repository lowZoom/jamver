package luj.game.server.internal.luj.lujcluster.actor.network.disconnect;

import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.network.disconnect.DisconnectHandleInvoker;

@Internal
final class OnConnDisconnect implements NetRootActor.Handler<ConnDisconnectMsg> {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);
    ConnDisconnectMsg msg = ctx.getMessage(this);

    DisconnectHandleInvoker.GET.invoke(self, ctx.getActorRef(), msg.getConnectionId());
  }
}
