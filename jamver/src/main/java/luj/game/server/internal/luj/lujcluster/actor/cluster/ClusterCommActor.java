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
      ClusterProtoPlugin protoPlugin, NodeStartListener.Actor dataRef) {
    _handlerMap = handlerMap;
    _protoPlugin = protoPlugin;
    _dataRef = dataRef;
  }

  public Map<String, ServerMessageHandler<?>> getHandlerMap() {
    return _handlerMap;
  }

  public ClusterProtoPlugin getProtoPlugin() {
    return _protoPlugin;
  }

  public NodeStartListener.Actor getDataRef() {
    return _dataRef;
  }

  private final Map<String, ServerMessageHandler<?>> _handlerMap;
  private final ClusterProtoPlugin _protoPlugin;

  private final NodeStartListener.Actor _dataRef;
}
