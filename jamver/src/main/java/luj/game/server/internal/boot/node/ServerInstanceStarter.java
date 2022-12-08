package luj.game.server.internal.boot.node;

import com.google.common.collect.ImmutableList;
import luj.bean.api.LujBean;
import luj.cache.api.LujCache;
import luj.cluster.api.ClusterSession;
import luj.cluster.api.LujCluster;
import luj.config.api.LujConfig;
import luj.game.server.api.plugin.JamverBootRootInit;
import luj.game.server.internal.boot.plugin.start.BootStartInvoker;
import luj.game.server.internal.inject.ServerBeanCollector;
import luj.game.server.internal.inject.ServerBeanRoot;
import luj.game.server.internal.luj.lujcluster.JamPluginCollect;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
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
      startCluster(lujcluster);

    } catch (Throwable t) {
      LOG.error(t.getMessage(), t);
    }
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.OnNodeStart#onStart
   */
  private void startCluster(ClusterSession lujcluster) throws Exception {
    ServerBeanRoot beanRoot = new ServerBeanCollector(_appContext).collect();
    JamverBootRootInit startPlugin = beanRoot.getBootInitPlugin();

    BootStartInvoker.Result startResult = new BootStartInvoker(startPlugin).invoke();
    BootStartInvoker.Config startCfg = startResult.config();

    BootStartInvoker.Cluster clusterCfg = startCfg.cluster();
    String host = clusterCfg.selfHost();
    int port = clusterCfg.selfPort();
    if (host == null) {
      LOG.debug("[game]未启用集群模块");
    }

    BootStartInvoker.Param appParam = startCfg.param();
    var allPlugin = new JamPluginCollect(beanRoot.getDataAllPlugin(),
        beanRoot.getClusterProtoPlugin(), beanRoot.getNetAllPlugin(),
        beanRoot.getDynamicInitPlugin(), beanRoot.getConfigReloadPlugin(),
        beanRoot.getBootShutdownPlugin());

    var jambean = new JambeanInLujcluster(beanRoot.getStartListenerList(),
        beanRoot.getDataCommandList(), beanRoot.getDataLoadList(), beanRoot.getCommandGroupList(),
        beanRoot.getClusterMsgHandleList(), beanRoot.getClusterJoinList(),
        beanRoot.getClusterHealthList(),
        ImmutableList.of(), beanRoot.getProtoHandlerList(),
        allPlugin, startResult.internal(),
        LujCache.start(), LujConfig.start(), LujBean.start(),
        appParam.start(), appParam.shutdown());

    lujcluster.startNode(c -> c
        .selfHost(host)
        .selfPort(port)
        .selfName(clusterCfg.selfName())
        .selfTags(clusterCfg.selfTags())
        .discoveryAkkaSeed(clusterCfg.akkaSeed())
        .discoveryConsulHost(clusterCfg.consulHost())
        .discoveryConsulPort(clusterCfg.consulPort())
        .startParam(jambean));
  }

  private static final Logger LOG = LoggerFactory.getLogger(ServerInstanceStarter.class);

  private final ApplicationContext _appContext;
}
