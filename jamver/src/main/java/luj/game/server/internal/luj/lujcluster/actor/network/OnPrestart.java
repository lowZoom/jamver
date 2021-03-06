package luj.game.server.internal.luj.lujcluster.actor.network;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import luj.ava.spring.Internal;
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

    List<BootStartInvoker.Network.Address> bindList = netCfg.bind();
    if (bindList.isEmpty()) {
      LOG.debug("未启用网络模块");
      return;
    }

    NetServer server = self.getLujnet().createServer();
    NetAllPlugin allPlugin = self.getAllPlugin();

    Actor selfRef = ctx.getActor();
    AtomicInteger nextConnId = new AtomicInteger(1);

    for (BootStartInvoker.Network.Address addr : bindList) {
      String host = addr.host();
      int port = addr.port();
      LOG.debug("网络绑定监听 -> {}:{}", host, port);

      server.bind(host, port, new JambeanInLujnet(allPlugin.getAcceptInit(),
          allPlugin.getReceiveFrame(), selfRef, nextConnId));
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnPrestart.class);
}
