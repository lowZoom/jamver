package luj.game.server.internal.luj.lujcluster;

import luj.game.server.api.plugin.JamverBootShutdown;
import luj.game.server.api.plugin.JamverConfigReload;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterProtoPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.DataAllPlugin;
import luj.game.server.internal.luj.lujcluster.actor.network.NetAllPlugin;

public class JamPluginCollect {

  public JamPluginCollect(DataAllPlugin dataAll, ClusterProtoPlugin clusterProto,
      NetAllPlugin netAll, JamverDynamicRootInit dynamicInit, JamverConfigReload configReload,
      JamverBootShutdown bootShutdown) {
    _dataAll = dataAll;
    _clusterProto = clusterProto;
    _netAll = netAll;
    _dynamicInit = dynamicInit;
    _configReload = configReload;
    _bootShutdown = bootShutdown;
  }

  public DataAllPlugin getDataAll() {
    return _dataAll;
  }

  public ClusterProtoPlugin getClusterProto() {
    return _clusterProto;
  }

  public NetAllPlugin getNetAll() {
    return _netAll;
  }

  public JamverDynamicRootInit getDynamicInit() {
    return _dynamicInit;
  }

  public JamverConfigReload getConfigReload() {
    return _configReload;
  }

  public JamverBootShutdown getBootShutdown() {
    return _bootShutdown;
  }

  private final DataAllPlugin _dataAll;
  private final ClusterProtoPlugin _clusterProto;
  private final NetAllPlugin _netAll;

  private final JamverDynamicRootInit _dynamicInit;
  private final JamverConfigReload _configReload;

  private final JamverBootShutdown _bootShutdown;
}
