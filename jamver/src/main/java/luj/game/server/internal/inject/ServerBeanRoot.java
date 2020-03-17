package luj.game.server.internal.inject;

import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.dataload.DataLoadPlugin;
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

  public List<ServerMessageHandler<?>> getClusterMessageList() {
    return _clusterMessageList;
  }

  public List<ServerJoinListener> getClusterJoinList() {
    return _clusterJoinList;
  }

  public DataLoadPlugin getDataLoadPlugin() {
    return _dataLoadPlugin;
  }

  @Autowired(required = false)
  private List<GameStartListener> _startListenerList;

  @Autowired(required = false)
  private List<GameDataCommand<?, ?>> _dataCommandList;

  @Autowired(required = false)
  private List<GameDataLoad<?, ?>> _dataLoadList;

  @Autowired(required = false)
  private List<ServerMessageHandler<?>> _clusterMessageList;

  @Autowired(required = false)
  private List<ServerJoinListener> _clusterJoinList;

  @Autowired
  private DataLoadPlugin _dataLoadPlugin;
}
