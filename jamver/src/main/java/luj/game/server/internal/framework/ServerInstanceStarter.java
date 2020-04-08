package luj.game.server.internal.framework;

import luj.cache.api.LujCache;
import luj.cluster.api.ClusterSession;
import luj.cluster.api.LujCluster;
import luj.game.server.api.plugin.JamverBootRootInit;
import luj.game.server.internal.boot.plugin.BootStartInvoker;
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
    try (AnnotationConfigApplicationContext internalCtx = new AnnotationConfigApplicationContext()) {
      internalCtx.getBeanFactory().registerSingleton(
          AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, new BeanNameGen());

      internalCtx.register(InternalInjectConf.class);
      internalCtx.refresh();

      ClusterSession lujcluster = LujCluster.start(internalCtx);
      startCluster(lujcluster, internalCtx);
    }
  }

  private void startCluster(ClusterSession lujcluster, ApplicationContext internalCtx) {
    ServerBeanRoot beanRoot = new ServerBeanCollector(_appContext).collect();
    JamverBootRootInit startPlugin = beanRoot.getBootInitPlugin();
    BootStartInvoker.Result startCfg = new BootStartInvoker(startPlugin).invoke();

    BootStartInvoker.Cluster clusterCfg = startCfg.clusterConfig();
    String host = clusterCfg.selfHost();
    int port = clusterCfg.selfPort();

    lujcluster.startNode(host, port, clusterCfg.seedList(), new JambeanInLujcluster(
        beanRoot.getStartListenerList(),
        beanRoot.getDataCommandList(),
        beanRoot.getDataLoadList(),
        null, null,
        beanRoot.getClusterMessageList(),
        beanRoot.getClusterJoinList(),
        beanRoot.getDataAllPlugin(),
        beanRoot.getClusterProtoPlugin(),
        LujCache.start(internalCtx),
        startCfg.startParam()));
  }

  private final ApplicationContext _appContext;
}
