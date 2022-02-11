package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.register;

import java.util.Collection;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;

public class AddMoreCommandMsg {

  public AddMoreCommandMsg(Collection<GameDataCommand<?, ?>> dataCommand,
      Collection<GameDataLoad<?, ?>> dataLoad) {
    _dataCommand = dataCommand;
    _dataLoad = dataLoad;
  }

  public Collection<GameDataCommand<?, ?>> getDataCommand() {
    return _dataCommand;
  }

  public Collection<GameDataLoad<?, ?>> getDataLoad() {
    return _dataLoad;
  }

  private final Collection<GameDataCommand<?, ?>> _dataCommand;

  private final Collection<GameDataLoad<?, ?>> _dataLoad;
}
