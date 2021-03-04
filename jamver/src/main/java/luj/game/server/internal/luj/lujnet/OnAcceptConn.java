package luj.game.server.internal.luj.lujnet;

import luj.ava.spring.Internal;
import luj.net.api.server.ConnectionAcceptInitializer;

@Internal
final class OnAcceptConn implements ConnectionAcceptInitializer {

  @Override
  public Object init(Context ctx) {
    JambeanInLujnet bindParam = ctx.getBindParam();
    return new NetConnState(bindParam);
  }
}
