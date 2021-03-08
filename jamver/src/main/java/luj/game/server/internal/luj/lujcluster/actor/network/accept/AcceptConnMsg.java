package luj.game.server.internal.luj.lujcluster.actor.network.accept;

import luj.net.api.server.ConnectionAcceptInitializer;
import luj.net.api.server.ConnectionAcceptInitializer.Address;

public class AcceptConnMsg {

  public AcceptConnMsg(Integer connectionId, ConnectionAcceptInitializer.Connection connection,
      Address bindAddr) {
    _connectionId = connectionId;
    _connection = connection;
    _bindAddr = bindAddr;
  }

  public Integer getConnectionId() {
    return _connectionId;
  }

  public ConnectionAcceptInitializer.Connection getConnection() {
    return _connection;
  }

  public ConnectionAcceptInitializer.Address getBindAddr() {
    return _bindAddr;
  }

  private final Integer _connectionId;
  private final ConnectionAcceptInitializer.Connection _connection;

  private final ConnectionAcceptInitializer.Address _bindAddr;
}
