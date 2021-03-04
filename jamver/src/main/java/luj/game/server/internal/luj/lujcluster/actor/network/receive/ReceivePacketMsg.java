package luj.game.server.internal.luj.lujcluster.actor.network.receive;

public class ReceivePacketMsg {

  public ReceivePacketMsg(Object packet) {
    _packet = packet;
  }

  public Object getPacket() {
    return _packet;
  }

  private final Object _packet;
}
