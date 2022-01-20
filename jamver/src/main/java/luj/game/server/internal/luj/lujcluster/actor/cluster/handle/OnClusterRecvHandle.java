package luj.game.server.internal.luj.lujcluster.actor.cluster.handle;

import luj.ava.spring.Internal;
import luj.game.server.internal.cluster.handle.ServmsgHandleInvoker;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;

@Internal
final class OnClusterRecvHandle implements ClusterCommActor.Handler<ClusterRecvHandleMsg> {

  @Override
  public void onHandle(Context ctx) {
    ClusterCommActor self = ctx.getActorState(this);
    ClusterRecvHandleMsg msg = ctx.getMessage(this);

    String msgKey = msg.getMessageKey();
    Object msgObj = msg.getMessageObj();
    Node remoteNode = msg.getRemoteNode();

    new ServmsgHandleInvoker(msgKey, msgObj, self.getHandlerMap(), self.getCommandMap(),
        remoteNode, ctx.getActorRef(), self.getSiblingRef().getDataRef(),
        self.getLujbean()).invoke();
  }
}
