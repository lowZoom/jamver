package luj.game.server.internal.luj.lujcluster.actor.network.disconnect;

public class ConnDisconnectMsg {

  public ConnDisconnectMsg(Integer connectionId) {
    _connectionId = connectionId;
  }

  public Integer getConnectionId() {
    return _connectionId;
  }

  private final Integer _connectionId;
}
