package luj.game.server.internal.luj.lujcluster.actor.network.send;

public class SendProtoMsg {

  public SendProtoMsg(Integer connectionId, Object proto) {
    _connectionId = connectionId;
    _proto = proto;
  }

  public Integer getConnectionId() {
    return _connectionId;
  }

  public Object getProto() {
    return _proto;
  }

  private final Integer _connectionId;

  private final Object _proto;
}
