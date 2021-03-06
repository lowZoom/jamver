package luj.game.server.internal.inject;

import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.api.net.NetAcceptHandler;
import luj.game.server.api.plugin.JamverBootRootInit;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterProtoPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.DataAllPlugin;
import luj.game.server.internal.luj.lujcluster.actor.network.NetAllPlugin;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see ServerBeanCollector#collect
 */
public class ServerBeanRoot {

  public List<GameStartListener> getStartListenerList() {
    return _startListenerList;
  }

  public List<GameDataCommand<?, ?>> getDataCommandList() {
    return _dataCommandList;
  }

  public List<GameDataLoad<?, ?>> getDataLoadList() {
    return _dataLoadList;
  }

  public List<GameDataCommandGroup> getCommandGroupList() {
    return _commandGroupList;
  }

  public List<ServerMessageHandler<?>> getClusterMessageList() {
    return _clusterMessageList;
  }

  public List<ServerJoinListener> getClusterJoinList() {
    return _clusterJoinList;
  }

  public NetAcceptHandler getNetAcceptHandler() {
    return _netAcceptHandler;
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

  public NetAllPlugin getNetReceivePlugin() {
    return _netReceivePlugin;
  }

  @Autowired(required = false)
  private List<GameStartListener> _startListenerList;

  @Autowired(required = false)
  private List<GameDataCommand<?, ?>> _dataCommandList;

  @Autowired(required = false)
  private List<GameDataLoad<?, ?>> _dataLoadList;

  @Autowired(required = false)
  private List<GameDataCommandGroup> _commandGroupList;

  @Autowired(required = false)
  private List<ServerMessageHandler<?>> _clusterMessageList;

  @Autowired(required = false)
  private List<ServerJoinListener> _clusterJoinList;

  @Autowired(required = false)
  private NetAcceptHandler _netAcceptHandler;

  @Autowired(required = false)
  private List<GameProtoHandler<?>> _protoHandlerList;

  @Autowired
  private JamverBootRootInit _bootInitPlugin;

  @Autowired
  private DataAllPlugin _dataAllPlugin;

  @Autowired
  private ClusterProtoPlugin _clusterProtoPlugin;

  @Autowired
  private NetAllPlugin _netReceivePlugin;
}
