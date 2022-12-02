package luj.game.server.internal.network.proto.handle.collect;

import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import luj.ava.reflect.type.TypeX;
import luj.ava.stream.StreamX;
import luj.game.server.api.net.GameProtoHandler;

public class ProtoHandleMapCollector {

  public ProtoHandleMapCollector(Collection<GameProtoHandler<?>> handlerList) {
    _handlerList = handlerList;
  }

  public Map<String, GameProtoHandler<?>> collect() {
    return StreamX.from(_handlerList)
        .collect(toMap(this::getProtoType, Function.identity()));
  }

  //TODO: 后面协议key提供插件式指定
  private String getProtoType(GameProtoHandler<?> handler) {
    return TypeX.ofInstance(handler)
        .getSupertype(GameProtoHandler.class)
        .getTypeParam(0)
        .asClass()
        .getName();
  }

  private final Collection<GameProtoHandler<?>> _handlerList;
}
