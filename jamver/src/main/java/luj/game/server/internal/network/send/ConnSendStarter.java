package luj.game.server.internal.network.send;

import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.luj.lujcluster.actor.network.send.SendProtoMsg;

public class ConnSendStarter {

  public ConnSendStarter(Integer connId, Tellable netRef, Object proto) {
    _connId = connId;
    _netRef = netRef;
    _proto = proto;
  }

  public void start() {
    SendProtoMsg msg = new SendProtoMsg(_connId, _proto);
    _netRef.tell(msg);
  }

  private final Integer _connId;
  private final Tellable _netRef;

  private final Object _proto;
}
