package luj.game.server.internal.network.send.encode;

import io.netty.buffer.ByteBuf;
import luj.game.server.api.plugin.JamverNetSendEncode;

public class SendEncodeInvoker {

  public SendEncodeInvoker(JamverNetSendEncode plugin, Object proto) {
    _plugin = plugin;
    _proto = proto;
  }

  public ByteBuf invoke() {
    ContextImpl ctx = new ContextImpl();
    ctx._proto = _proto;

    return _plugin.encode(ctx);
  }

  private final JamverNetSendEncode _plugin;

  private final Object _proto;
}
