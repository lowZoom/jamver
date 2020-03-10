package luj.game.server.internal.data.command.collect;

import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.GameplayDataActor;
import org.slf4j.Logger;

final class KitImpl implements GameplayDataActor.CommandKit {

  KitImpl(GameDataCommand<?, ?> command, Class<?> commandType, GameDataLoad<?, ?> load) {
    _command = command;
    _commandType = commandType;
    _load = load;
  }

  public Class<?> getCommandType() {
    return _commandType;
  }

  @Override
  public GameDataCommand<?, ?> getCommand() {
    return _command;
  }

  @Override
  public GameDataLoad<?, ?> getLoad() {
    return _load;
  }

  @Override
  public Logger getLogger() {
    return null;
  }

  private final GameDataCommand<?, ?> _command;
  private final Class<?> _commandType;

  private final GameDataLoad<?, ?> _load;
}
