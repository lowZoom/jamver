package luj.game.server.internal.luj.lujcluster.actor.network.accept;

import luj.net.api.server.ConnectionAcceptInitializer;

public class AcceptConnMsg {

  public AcceptConnMsg(Integer connectionId, ConnectionAcceptInitializer.Address bindAddr) {
    _connectionId = connectionId;
    _bindAddr = bindAddr;
  }

  public Integer getConnectionId() {
    return _connectionId;
  }

  public ConnectionAcceptInitializer.Address getBindAddr() {
    return _bindAddr;
  }

  private final Integer _connectionId;

  private final ConnectionAcceptInitializer.Address _bindAddr;
}
