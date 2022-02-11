package luj.game.server.internal.cluster.handle.collect;

import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import luj.ava.reflect.type.TypeX;
import luj.ava.stream.StreamX;
import luj.game.server.api.cluster.ServerMessageHandler;

public class ClusterHandleMapCollector {

  public ClusterHandleMapCollector(Collection<ServerMessageHandler<?>> handlerList) {
    _handlerList = handlerList;
  }

  public Map<String, ServerMessageHandler<?>> collect() {
    return StreamX.from(_handlerList)
        .collect(toMap(this::getMessageKey, Function.identity()));
  }

  private String getMessageKey(ServerMessageHandler<?> handler) {
    return TypeX.ofInstance(handler)
        .getSupertype(ServerMessageHandler.class)
        .getTypeParam(0)
        .asClass()
        .getName();
  }

  private final Collection<ServerMessageHandler<?>> _handlerList;
}
