package luj.game.server.internal.network.receive.packet;

import luj.game.server.api.plugin.JamverNetReceivePacket;

final class ResultImpl implements JamverNetReceivePacket.Result, JamPacketReceiveInvoker.Result {

  @Override
  public JamverNetReceivePacket.Result protoType(Class<?> type) {
    _protoType = type;
    return this;
  }

  @Override
  public JamverNetReceivePacket.Result protoObj(Object proto) {
    _protoObj = proto;
    return this;
  }

  //////////////////////////////////////

  @Override
  public String protoType() {
    return _protoType.getName();
  }

  @Override
  public Object protoInstance() {
    return _protoObj;
  }

  //////////////////////////////////////

  Class<?> _protoType;

  Object _protoObj;
}
