package luj.game.server.internal.inject;

import java.util.List;
import luj.ava.spring.Internal;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import org.springframework.beans.factory.annotation.Autowired;

@Internal
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
}
