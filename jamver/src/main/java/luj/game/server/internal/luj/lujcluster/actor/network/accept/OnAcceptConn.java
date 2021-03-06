package luj.game.server.internal.luj.lujcluster.actor.network.accept;

import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.network.accept.AcceptHandleInvoker;

@Internal
final class OnAcceptConn implements NetRootActor.Handler<AcceptConnMsg> {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);
    AcceptConnMsg msg = ctx.getMessage(this);

    AcceptHandleInvoker.GET.invoke(self, msg);
  }
}
