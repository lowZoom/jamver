package luj.game.server.internal.dynamic.init;

import java.util.Collection;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.luj.lujcluster.actor.cluster.register.handler.AddMoreHandlerMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.register.AddMoreCommandMsg;

final class ContextImpl implements JamverDynamicRootInit.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getStartParam() {
    return (T) _startParam;
  }

  @Override
  public void registerDataCommand(Collection<GameDataCommand<?, ?>> command,
      Collection<GameDataLoad<?, ?>> load) {
    _dataRef.tell(new AddMoreCommandMsg(command, load));
  }

  @Override
  public void registerServerMsgHandler(Collection<ServerMessageHandler<?>> handler) {
    _clusterRef.tell(new AddMoreHandlerMsg(handler));
  }

  Object _startParam;

  Tellable _dataRef;
  Tellable _clusterRef;
}
