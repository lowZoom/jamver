package luj.game.server.api.plugin;

import io.netty.buffer.ByteBuf;

public interface JamverNetSendEncode {

  interface Context {

    Object getProto();
  }

  ByteBuf encode(Context ctx);
}
