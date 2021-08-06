package luj.game.server.internal.data.execute.service.time;

import luj.game.server.api.data.GameDataCommand;

public enum TimeServiceFactory {
  GET;

  public GameDataCommand.Time create() {
    return TimeServiceImpl.GET;
  }
}
