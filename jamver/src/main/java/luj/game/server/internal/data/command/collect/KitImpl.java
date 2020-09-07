package luj.game.server.internal.data.command.collect;

import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.Logger;

final class KitImpl implements GameplayDataActor.CommandKit {

  @Override
  public GameDataCommand<?, ?> getCommand() {
    return _command;
  }

  @Override
  public Class<?> getCommandType() {
    return _commandType;
  }

  @Override
  public Class<?> getParamType() {
    return _paramType;
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

  GameDataCommand<?, ?> _command;
  Class<?> _commandType;
  Class<?> _paramType;

  GameDataLoad<?, ?> _loader;
  Class<?> _loadResultType;

  Logger _logger;
}
