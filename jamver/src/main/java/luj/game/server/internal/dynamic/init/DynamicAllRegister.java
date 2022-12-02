package luj.game.server.internal.dynamic.init;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.stream.Collectors;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.luj.lujcluster.actor.cluster.register.dynamic.handler.AddMessageHandlerMsg;
import luj.game.server.internal.luj.lujcluster.actor.cluster.register.dynamic.join.AddJoinListenerMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.register.AddMoreCommandMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.register.AddEventListenerMsg;
import luj.game.server.internal.luj.lujcluster.actor.network.register.dynamic.AddProtoHandleMsg;

final class DynamicAllRegister {

  DynamicAllRegister(Collection<?> registerList, Tellable dataRef, Tellable eventRef,
      Tellable networkRef, Tellable clusterRef) {
    _registerList = registerList;
    _dataRef = dataRef;
    _eventRef = eventRef;
    _networkRef = networkRef;
    _clusterRef = clusterRef;
  }

  public Collection<GameStartListener> register() {
    if (_registerList.isEmpty()) {
      return ImmutableList.of();
    }

    _dataRef.tell(new AddMoreCommandMsg(
        findBeans(GameDataCommand.class), findBeans(GameDataLoad.class)));

    _eventRef.tell(new AddEventListenerMsg(findBeans(GameEventListener.class)));
    _networkRef.tell(new AddProtoHandleMsg(findBeans(GameProtoHandler.class)));

    _clusterRef.tell(new AddJoinListenerMsg(findBeans(ServerJoinListener.class)));
    _clusterRef.tell(new AddMessageHandlerMsg(findBeans(ServerMessageHandler.class)));

    return findBeans(GameStartListener.class);
  }

  @SuppressWarnings("unchecked")
  private <T> Collection<T> findBeans(Class<? super T> type) {
    return _registerList.stream()
        .filter(c -> type.isAssignableFrom(c.getClass()))
        .map(c -> (T) c)
        .collect(Collectors.toList());
  }

  private final Collection<?> _registerList;

  private final Tellable _dataRef;

  private final Tellable _eventRef;
  private final Tellable _networkRef;

  private final Tellable _clusterRef;
}
