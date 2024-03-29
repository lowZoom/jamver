package luj.game.server.internal.inject;

import luj.game.server.internal.data.id.state.DataIdPlugin;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterProtoPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.DataAllPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSavePlugin;
import luj.game.server.internal.luj.lujcluster.actor.network.NetAllPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerBeanCollector {

  public ServerBeanCollector(ApplicationContext externalCtx) {
    _externalCtx = externalCtx;
  }

  public ServerBeanRoot collect() {
    try (var ctx = new AnnotationConfigApplicationContext()) {
      ctx.setParent(_externalCtx);

      ctx.register(DataAllPlugin.class);
      ctx.register(DataLoadPlugin.class);
      ctx.register(DataSavePlugin.class);
      ctx.register(DataIdPlugin.class);

      ctx.register(ClusterProtoPlugin.class);
      ctx.register(NetAllPlugin.class);

      ctx.register(DataCommandCollect.class);
      ctx.register(ServerBeanRoot.class);
      ctx.refresh();

      return ctx.getBean(ServerBeanRoot.class);
    }
  }

  private final ApplicationContext _externalCtx;
}
