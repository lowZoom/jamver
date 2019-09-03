package luj.game.server.internal.luj.lujcluster.actor.cluster;

import java.util.Map;
import luj.game.server.api.cluster.ServerMessageHandler;

public class ClusterCommActor {

  public ClusterCommActor(Map<String, ServerMessageHandler<?>> handlerMap) {
    _handlerMap = handlerMap;
  }

  public Map<String, ServerMessageHandler<?>> getHandlerMap() {
    return _handlerMap;
  }

  private final Map<String, ServerMessageHandler<?>> _handlerMap;
}
