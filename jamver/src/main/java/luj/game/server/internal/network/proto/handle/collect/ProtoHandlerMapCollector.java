package luj.game.server.internal.network.proto.handle.collect;

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

  public Map<Class<?>, GameProtoHandler<?>> collect() {
    return StreamX.from(_handlerList)
        .collect(Collectors.toMap(this::getProtoType, Function.identity()));
  }

  private Class<?> getProtoType(GameProtoHandler<?> handler) {
    return TypeX.ofInstance(handler)
        .getSupertype(GameProtoHandler.class)
        .getTypeParam(0)
        .asClass();
  }

  private final List<GameProtoHandler<?>> _handlerList;
}
