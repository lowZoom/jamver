package luj.game.server.internal.luj.lujcluster.actor.network.receive;

public class ReceivePacketMsg {

  public ReceivePacketMsg(Integer connectionId, Object packet) {
    _connectionId = connectionId;
    _packet = packet;
  }

  public Integer getConnectionId() {
    return _connectionId;
  }

  public Object getPacket() {
    return _packet;
  }

  private final Integer _connectionId;

  private final Object _packet;
}
