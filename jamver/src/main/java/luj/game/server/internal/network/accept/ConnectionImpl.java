package luj.game.server.internal.network.accept;

import luj.cluster.api.actor.Tellable;
import luj.game.server.api.net.NetAcceptHandler;
import luj.game.server.internal.network.send.ConnSendStarter;
import luj.net.api.server.ConnectionAcceptInitializer;

final class ConnectionImpl implements NetAcceptHandler.Connection, NetAcceptHandler.Address {

  @Override
  public Integer id() {
    return _connId;
  }

  @Override
  public <P> void send(Object proto) {
    new ConnSendStarter(_connId, _netRef, proto).start();
  }

  ///////////////////////////////////////

  @Override
  public String host() {
    return _bindAddr.host();
  }

  @Override
  public int port() {
    return _bindAddr.port();
  }

  ///////////////////////////////////////

  Integer _connId;
  Tellable _netRef;

  ConnectionAcceptInitializer.Address _bindAddr;
}
