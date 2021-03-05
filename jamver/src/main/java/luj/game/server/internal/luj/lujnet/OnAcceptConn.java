package luj.game.server.internal.luj.lujnet;

import java.util.concurrent.atomic.AtomicInteger;
import luj.ava.spring.Internal;
import luj.net.api.server.ConnectionAcceptInitializer;

@Internal
final class OnAcceptConn implements ConnectionAcceptInitializer {

  @Override
  public Object init(Context ctx) {
    JambeanInLujnet bindParam = ctx.getBindParam();

    AtomicInteger nextId = bindParam.getNextConnectionId();
    Integer connId = nextId.getAndIncrement();

    return new NetConnState(connId, bindParam);
  }
}
