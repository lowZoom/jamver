package luj.game.server.internal.network.proto.handle.collect;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import luj.ava.reflect.type.TypeX;
import luj.ava.stream.StreamX;
import luj.game.server.api.net.GameProtoHandler;

public class ProtoHandlerMapCollector {

  public ProtoHandlerMapCollector(List<GameProtoHandler<?>> handlerList) {
    _handlerList = handlerList;
  }

  public Map<String, GameProtoHandler<?>> collect() {
    return StreamX.from(_handlerList)
        .collect(toMap(this::getProtoType, Function.identity()));
  }

  private String getProtoType(GameProtoHandler<?> handler) {
    return TypeX.ofInstance(handler)
        .getSupertype(GameProtoHandler.class)
        .getTypeParam(0)
        .asClass()
        .getName();
  }

  private final List<GameProtoHandler<?>> _handlerList;
}
