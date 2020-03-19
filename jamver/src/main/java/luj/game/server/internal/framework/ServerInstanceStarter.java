package luj.game.server.internal.framework;

import luj.cache.api.LujCache;
import luj.cluster.api.ClusterSession;
import luj.cluster.api.LujCluster;
import luj.game.server.internal.inject.ServerBeanCollector;
import luj.game.server.internal.inject.ServerBeanRoot;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;

public class ServerInstanceStarter {

  public ServerInstanceStarter(ApplicationContext appContext) {
    _appContext = appContext;
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.OnLujclusterStart#onStart
   */
  public void start() {
    ServerBeanRoot beanRoot = new ServerBeanCollector(_appContext).collect();

    try (AnnotationConfigApplicationContext internalCtx = new AnnotationConfigApplicationContext()) {
      internalCtx.getBeanFactory().registerSingleton(
          AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, new BeanNameGen());

      internalCtx.register(InternalInjectConf.class);
      internalCtx.refresh();

      ClusterSession lujcluster = LujCluster.start(internalCtx);
      lujcluster.startNode("127.0.0.1", 2555, "127.0.0.1:2555", new JambeanInLujcluster(
          beanRoot.getStartListenerList(),
          beanRoot.getDataCommandList(),
          beanRoot.getDataLoadList(),
          null, null,
          beanRoot.getClusterMessageList(),
          beanRoot.getClusterJoinList(),
          beanRoot.getDataInitPlugin(),
          beanRoot.getDataLoadPlugin(),
          beanRoot.getDataSavePlugin(),
          LujCache.start(internalCtx)));
    }
  }

  private final ApplicationContext _appContext;
}
