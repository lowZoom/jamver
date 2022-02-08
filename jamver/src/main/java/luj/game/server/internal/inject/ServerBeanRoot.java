package luj.game.server.internal.inject;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.net.GameAcceptHandler;
import luj.game.server.api.net.GameDisconnectHandler;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.api.plugin.JamverBootRootInit;
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

  public GameAcceptHandler getNetAcceptHandler() {
    return _netAcceptHandler;
  }

  public GameDisconnectHandler getNetDisconnectHandler() {
    return _netDisconnectHandler;
  }

  public List<GameProtoHandler<?>> getProtoHandlerList() {
    return _protoHandlerList;
  }

  public JamverBootRootInit getBootInitPlugin() {
    return _bootInitPlugin;
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

  private <T> List<T> nonNull(List<T> list) {
    return MoreObjects.firstNonNull(list, ImmutableList.of());
  }

  @Autowired(required = false)
  private List<GameStartListener> _startListenerList;

  @Autowired
  private DataCommandCollect _dataCommandCollect;

  @Autowired(required = false)
  private List<ServerMessageHandler<?>> _clusterMsgHandleList;

  @Autowired(required = false)
  private List<ServerJoinListener> _clusterJoinList;

  @Autowired(required = false)
  private GameAcceptHandler _netAcceptHandler;

  @Autowired(required = false)
  private GameDisconnectHandler _netDisconnectHandler;

  @Autowired(required = false)
  private List<GameProtoHandler<?>> _protoHandlerList;

  @Autowired
  private JamverBootRootInit _bootInitPlugin;

  @Autowired
  private DataAllPlugin _dataAllPlugin;

  @Autowired
  private ClusterProtoPlugin _clusterProtoPlugin;

  @Autowired
  private NetAllPlugin _netAllPlugin;

  @Autowired(required = false)
  private JamverDynamicRootInit _dynamicInitPlugin;
}
