package luj.game.server.internal.network.proto.handle.collect;

import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import luj.ava.reflect.type.TypeX;
import luj.ava.stream.StreamX;
import luj.game.server.api.net.GameProtoHandler;

public class ProtoHandleCollector {

  public interface Result {

    ProtoHandleMap handleMap();

    List<GameProtoHandler.Default> defaultHandler();
  }

  public ProtoHandleCollector(Collection<GameProtoHandler<?>> handlerList) {
    _handlerList = handlerList;
  }

  public Result collect() {
    var handleMap = new HandleMapImpl();
    handleMap._handleMap = StreamX.from(_handlerList)
        .collect(toMap(this::getProtoType, Function.identity()));

    List<GameProtoHandler.Default> defaultHandler = StreamX.from(_handlerList)
        .filter(h -> h instanceof GameProtoHandler.Default)
        .map(h -> (GameProtoHandler.Default) h)
        .toList();

    return new Result() {
      @Override
      public ProtoHandleMap handleMap() {
        return handleMap;
      }

      @Override
      public List<GameProtoHandler.Default> defaultHandler() {
        return defaultHandler;
      }
    };
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
