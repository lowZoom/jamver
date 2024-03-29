package luj.game.server.internal.inject;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerHealthListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.api.plugin.JamverBootRootInit;
import luj.game.server.api.plugin.JamverBootShutdown;
import luj.game.server.api.plugin.JamverConfigReload;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterProtoPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.DataAllPlugin;
import luj.game.server.internal.luj.lujcluster.actor.network.NetAllPlugin;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see ServerBeanCollector#collect
 */
public class ServerBeanRoot {

  public List<GameStartListener> getStartListenerList() {
    return nonNull(_startListenerList);
  }

  public List<GameDataCommand<?, ?>> getDataCommandList() {
    return _dataCommandCollect.getDataCommandList();
  }

  public List<GameDataLoad<?, ?>> getDataLoadList() {
    return _dataCommandCollect.getDataLoadList();
  }

  public List<GameDataCommandGroup> getCommandGroupList() {
    return _dataCommandCollect.getCommandGroupList();
  }

  public List<ServerMessageHandler<?>> getClusterMsgHandleList() {
    return nonNull(_clusterMsgHandleList);
  }

  public List<ServerJoinListener> getClusterJoinList() {
    return nonNull(_clusterJoinList);
  }

  public List<ServerHealthListener> getClusterHealthList() {
    return nonNull(_clusterHealthList);
  }

  public List<GameProtoHandler<?>> getProtoHandlerList() {
    return _protoHandlerList;
  }

  public JamverBootRootInit getBootInitPlugin() {
    return _bootInitPlugin;
  }

  public JamverBootShutdown getBootShutdownPlugin() {
    return _bootShutdownPlugin;
  }

  public DataAllPlugin getDataAllPlugin() {
    return _dataAllPlugin;
  }

  public ClusterProtoPlugin getClusterProtoPlugin() {
    return _clusterProtoPlugin;
  }

  public NetAllPlugin getNetAllPlugin() {
    return _netAllPlugin;
  }

  public JamverDynamicRootInit getDynamicInitPlugin() {
    return _dynamicInitPlugin;
  }

  public JamverConfigReload getConfigReloadPlugin() {
    return _configReloadPlugin;
  }

  private <T> List<T> nonNull(List<T> list) {
    return MoreObjects.firstNonNull(list, ImmutableList.of());
  }

  @Autowired(required = false)
  List<GameStartListener> _startListenerList;

  @Autowired
  DataCommandCollect _dataCommandCollect;

  @Autowired(required = false)
  List<ServerJoinListener> _clusterJoinList;

  @Autowired(required = false)
  List<ServerHealthListener> _clusterHealthList;

  @Autowired(required = false)
  List<ServerMessageHandler<?>> _clusterMsgHandleList;

  @Autowired(required = false)
  List<GameProtoHandler<?>> _protoHandlerList;

  @Autowired
  JamverBootRootInit _bootInitPlugin;

  @Autowired(required = false)
  JamverBootShutdown _bootShutdownPlugin;

  @Autowired
  DataAllPlugin _dataAllPlugin;

  @Autowired
  ClusterProtoPlugin _clusterProtoPlugin;

  @Autowired
  NetAllPlugin _netAllPlugin;

  @Autowired(required = false)
  JamverDynamicRootInit _dynamicInitPlugin;

  @Autowired(required = false)
  JamverConfigReload _configReloadPlugin;
}
