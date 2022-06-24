package luj.game.server.internal.dynamic.init;

import java.util.Collection;
import java.util.stream.Collectors;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.event.GameEventListener;
import luj.game.server.internal.luj.lujcluster.actor.cluster.register.dynamic.handler.AddMessageHandlerMsg;
import luj.game.server.internal.luj.lujcluster.actor.cluster.register.dynamic.join.AddJoinListenerMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.register.AddMoreCommandMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.event.register.AddEventListenerMsg;

final class DynamicAllRegister {

  DynamicAllRegister(Collection<?> registerList, Tellable eventRef, Tellable dataRef,
      Tellable clusterRef) {
    _registerList = registerList;
    _eventRef = eventRef;
    _dataRef = dataRef;
    _clusterRef = clusterRef;
  }

  void register() {
    if (_registerList.isEmpty()) {
      return;
    }

    _eventRef.tell(new AddEventListenerMsg(findBeans(GameEventListener.class)));

    _dataRef.tell(new AddMoreCommandMsg(
        findBeans(GameDataCommand.class), findBeans(GameDataLoad.class)));

    _clusterRef.tell(new AddJoinListenerMsg(findBeans(ServerJoinListener.class)));
    _clusterRef.tell(new AddMessageHandlerMsg(findBeans(ServerMessageHandler.class)));
  }

  @SuppressWarnings("unchecked")
  private <T> Collection<T> findBeans(Class<? super T> type) {
    return _registerList.stream()
        .filter(c -> type.isAssignableFrom(c.getClass()))
        .map(c -> (T) c)
        .collect(Collectors.toList());
  }

  private final Collection<?> _registerList;

  private final Tellable _eventRef;
  private final Tellable _dataRef;

  private final Tellable _clusterRef;
}
