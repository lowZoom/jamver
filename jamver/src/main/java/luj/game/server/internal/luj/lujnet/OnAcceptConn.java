package luj.game.server.internal.luj.lujnet;

import java.util.concurrent.atomic.AtomicInteger;
import luj.ava.spring.Internal;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.plugin.JamverNetAcceptInit;
import luj.game.server.internal.luj.lujcluster.actor.network.accept.AcceptConnMsg;
import luj.net.api.server.ConnectionAcceptInitializer;

@Internal
final class OnAcceptConn implements ConnectionAcceptInitializer {

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.network.accept.OnAcceptConn
   */
  @Override
  public Object init(Context ctx) {
    JambeanInLujnet bindParam = ctx.getBindParam();

    AtomicInteger nextId = bindParam.getNextConnectionId();
    Integer connId = nextId.getAndIncrement();

    Tellable netRef = bindParam.getNetRef();
    netRef.tell(new AcceptConnMsg(connId, ctx.getConnection(), ctx.getBindAddress()));

    JamverNetAcceptInit initPlugin = bindParam.getAcceptInitPlugin();
    Object pluginState = initPlugin.onInit(null);

    return new NetConnState(connId, pluginState, bindParam);
  }
}
