package luj.game.server.internal.luj.lujcluster.actor.network;

import java.util.concurrent.atomic.AtomicInteger;
import luj.ava.spring.Internal;
import luj.game.server.api.plugin.JamverNetReceiveFrame;
import luj.game.server.internal.boot.plugin.BootStartInvoker;
import luj.game.server.internal.luj.lujnet.JambeanInLujnet;
import luj.net.api.server.NetServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnPrestart implements NetRootActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);
    BootStartInvoker.Network netCfg = self.getNetParam();
    NetServer server = self.getLujnet().createServer();

    NetReceivePlugin receivePlugin = self.getReceivePlugin();
    JamverNetReceiveFrame framePlugin = receivePlugin.getReceiveFrame();

    Actor selfRef = ctx.getActor();
    AtomicInteger nextConnId = new AtomicInteger(1);

    for (BootStartInvoker.Network.Address addr : netCfg.bind()) {
      String host = addr.host();
      int port = addr.port();
      LOG.debug("网络绑定监听 -> {}:{}", host, port);

      JambeanInLujnet param = new JambeanInLujnet(framePlugin, selfRef, nextConnId);
      server.bind(host, port, param);
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnPrestart.class);
}
