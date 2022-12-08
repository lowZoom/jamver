package luj.game.server.internal.network.proto.handle.collect;

import luj.game.server.api.net.GameProtoHandler;

public interface ProtoHandleMap {

  GameProtoHandler<?> getHandler(String protoKey);

  void putAll(ProtoHandleMap addMap);

  int size();
}
