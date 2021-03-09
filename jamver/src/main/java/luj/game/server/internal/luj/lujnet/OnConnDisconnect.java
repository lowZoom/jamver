package luj.game.server.internal.luj.lujnet;

import luj.ava.spring.Internal;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.luj.lujcluster.actor.network.disconnect.ConnDisconnectMsg;
import luj.net.api.connection.NetDisconnectListener;

@Internal
final class OnConnDisconnect implements NetDisconnectListener {

  @Override
  public void onDisconnect(Context ctx) {
    NetConnState connState = ctx.getConnectionState();
    Tellable netRef = connState.getBindParam().getNetRef();

    ConnDisconnectMsg msg = new ConnDisconnectMsg(connState.getConnectionId());
    netRef.tell(msg);
  }
}
