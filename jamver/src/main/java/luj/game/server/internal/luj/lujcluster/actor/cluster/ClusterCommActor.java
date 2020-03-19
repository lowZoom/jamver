package luj.game.server.internal.luj.lujcluster.actor.cluster;

import java.util.Map;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.cluster.ServerMessageHandler;

public class ClusterCommActor {

  public interface Handler<M> extends ActorMessageHandler<ClusterCommActor, M> {
    // NOOP
  }

  public ClusterCommActor(Map<String, ServerMessageHandler<?>> handlerMap,
      NodeStartListener.Actor dataRef) {
    _handlerMap = handlerMap;
    _dataRef = dataRef;
  }

  public Map<String, ServerMessageHandler<?>> getHandlerMap() {
    return _handlerMap;
  }

  public NodeStartListener.Actor getDataRef() {
    return _dataRef;
  }

  private final Map<String, ServerMessageHandler<?>> _handlerMap;

  private final NodeStartListener.Actor _dataRef;
}
