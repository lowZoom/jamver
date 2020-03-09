package luj.game.server.internal.framework;

import luj.cluster.api.LujCluster;
import luj.game.server.internal.inject.ServerBeanCollector;
import luj.game.server.internal.inject.ServerBeanRoot;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerInstanceStarter {

  public ServerInstanceStarter(ApplicationContext appContext) {
    _appContext = appContext;
  }

  public void start() {
    ServerBeanRoot beanRoot = new ServerBeanCollector(_appContext).collect();

    try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
//      ctx.setParent(_appContext);

      ctx.register(InternalInjectConf.class);
      ctx.refresh();

      LujCluster.start(ctx).startNode("127.0.0.1", 2555, "127.0.0.1:2555", new JambeanInLujcluster(
          beanRoot.getStartListenerList(),
          null,
          null,
          beanRoot.getClusterMessageList(),
          beanRoot.getClusterJoinList()));
    }
  }

  private final ApplicationContext _appContext;
}
