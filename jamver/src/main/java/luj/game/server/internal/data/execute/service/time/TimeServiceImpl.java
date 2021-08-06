package luj.game.server.internal.data.execute.service.time;

import luj.game.server.api.data.GameDataCommand;

enum TimeServiceImpl implements GameDataCommand.Time {
  GET;

  @Override
  public long now() {
    return System.currentTimeMillis();
  }
}
