package luj.game.server.internal.network.send.encode;

import luj.game.server.api.plugin.JamverNetSendEncode;

final class ContextImpl implements JamverNetSendEncode.Context {

  @Override
  public Object getProto() {
    return _proto;
  }

  Object _proto;
}
