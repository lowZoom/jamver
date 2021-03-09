package luj.game.server.internal.luj.lujcluster.actor.network.close;

public class CloseConnMsg {

  public CloseConnMsg(Integer connectionId) {
    _connectionId = connectionId;
  }

  public Integer getConnectionId() {
    return _connectionId;
  }

  private final Integer _connectionId;
}
