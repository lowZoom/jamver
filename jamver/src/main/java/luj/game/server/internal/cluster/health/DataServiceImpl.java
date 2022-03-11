package luj.game.server.internal.cluster.health;

import java.util.Map;
import luj.game.server.api.cluster.ServerHealthListener;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.command.service.CommandServiceFactory;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

final class DataServiceImpl implements ServerHealthListener.Data {

  @Override
  public <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType) {
    return new CommandServiceFactory(null, null, null, commandType, _commandMap).create();
  }

  Map<String, GameplayDataActor.CommandKit> _commandMap;
}
