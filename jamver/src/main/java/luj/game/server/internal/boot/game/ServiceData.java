package luj.game.server.internal.boot.game;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.command.service.CommandServiceFactory;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

final class ServiceData implements GameStartListener.Data {

  @SuppressWarnings("unchecked")
  @Override
  public void executeCommand(Class<? extends GameDataCommand<?, ?>> commandType) {
    command((Class<? extends GameDataCommand<Object, ?>>) commandType).execute((b, p) -> b);
  }

  @Override
  public <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType) {
    return new CommandServiceFactory(_lujbean, _dataRef, null, commandType, _commandMap).create();
  }

  Map<String, GameplayDataActor.CommandKit> _commandMap;
  BeanContext _lujbean;

  Tellable _dataRef;
}
