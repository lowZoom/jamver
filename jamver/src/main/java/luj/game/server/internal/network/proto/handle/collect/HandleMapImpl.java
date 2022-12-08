package luj.game.server.internal.network.proto.handle.collect;

import java.util.Map;
import luj.game.server.api.net.GameProtoHandler;

final class HandleMapImpl implements ProtoHandleMap {

  @Override
  public GameProtoHandler<?> getHandler(String protoKey) {
    return _handleMap.get(protoKey);
  }

  @Override
  public void putAll(ProtoHandleMap addMap) {
    var addImpl = (HandleMapImpl) addMap;
    _handleMap.putAll(addImpl._handleMap);
  }

  @Override
  public int size() {
    return _handleMap.size();
  }

  Map<String, GameProtoHandler<?>> _handleMap;
}
