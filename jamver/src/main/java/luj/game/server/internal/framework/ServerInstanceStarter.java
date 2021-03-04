package luj.game.server.internal.framework;

import luj.bean.api.LujBean;
import luj.cache.api.LujCache;
import luj.cluster.api.ClusterSession;
import luj.cluster.api.LujCluster;
import luj.game.server.api.plugin.JamverBootRootInit;
import luj.game.server.internal.boot.plugin.BootStartInvoker;
import luj.game.server.internal.inject.ServerBeanCollector;
import luj.game.server.internal.inject.ServerBeanRoot;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import luj.net.api.LujNet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerInstanceStarter {

  public ServerInstanceStarter(ApplicationContext appContext) {
    _appContext = appContext;
  }

  public void start() {
    try (AnnotationConfigApplicationContext internalCtx = new SpringContextCreator().create()) {
      internalCtx.register(InternalInjectConf.class);
      internalCtx.refresh();

      ClusterSession lujcluster = LujCluster.start(internalCtx);
      startCluster(lujcluster, internalCtx);
    }
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.OnNodeStart#onStart
   */
  private void startCluster(ClusterSession lujcluster, ApplicationContext internalCtx) {
    ServerBeanRoot beanRoot = new ServerBeanCollector(_appContext).collect();
    JamverBootRootInit startPlugin = beanRoot.getBootInitPlugin();
    BootStartInvoker.Result startCfg = new BootStartInvoker(startPlugin).invoke();

    BootStartInvoker.Cluster clusterCfg = startCfg.clusterConfig();
    String host = clusterCfg.selfHost();
    int port = clusterCfg.selfPort();

    if (host == null) {
      LOG.debug("未启用集群模块");
    }

    lujcluster.startNode(host, port, clusterCfg.seedList(), new JambeanInLujcluster(
        beanRoot.getStartListenerList(),
        beanRoot.getDataCommandList(), beanRoot.getDataLoadList(),
        beanRoot.getCommandGroupList(), null, null,
        beanRoot.getClusterMessageList(), beanRoot.getClusterJoinList(),
        beanRoot.getDataAllPlugin(),
        beanRoot.getClusterProtoPlugin(),
        beanRoot.getNetReceivePlugin(),
        LujCache.start(internalCtx), LujBean.start(),
        LujNet.create(internalCtx), startCfg.networkConfig(),
        startCfg.startParam()));
  }

  private static final Logger LOG = LoggerFactory.getLogger(ServerInstanceStarter.class);

  private final ApplicationContext _appContext;
}
