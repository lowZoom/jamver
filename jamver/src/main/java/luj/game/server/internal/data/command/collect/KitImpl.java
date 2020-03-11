package luj.game.server.internal.data.command.collect;

import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.GameplayDataActor;
import org.slf4j.Logger;

final class KitImpl implements GameplayDataActor.CommandKit {

  KitImpl(GameDataCommand<?, ?> command, Class<?> commandType, GameDataLoad<?, ?> loader,
      Class<?> loadResultType, Logger logger) {
    _command = command;
    _commandType = commandType;
    _loader = loader;
    _loadResultType = loadResultType;
    _logger = logger;
  }

  public Class<?> getCommandType() {
    return _commandType;
  }

  @Override
  public GameDataCommand<?, ?> getCommand() {
    return _command;
  }

  @Override
  public GameDataLoad<?, ?> getLoader() {
    return _loader;
  }

  @Override
  public Class<?> getLoadResultType() {
    return _loadResultType;
  }

  @Override
  public Logger getLogger() {
    return _logger;
  }

  private final GameDataCommand<?, ?> _command;
  private final Class<?> _commandType;

  private final GameDataLoad<?, ?> _loader;
  private final Class<?> _loadResultType;

  private final Logger _logger;
}
