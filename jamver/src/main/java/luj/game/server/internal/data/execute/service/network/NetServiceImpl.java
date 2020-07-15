package luj.game.server.internal.data.execute.service.network;

import luj.game.server.api.data.GameDataCommand;

final class NetServiceImpl implements GameDataCommand.Network {

  @Override
  public GameDataCommand.Session session() {
    return _session;
  }

  @Override
  public GameDataCommand.Session session(Comparable<?> sessionId) {
    return null;
  }

  @Override
  public GameDataCommand.Http http() {
    return null;
  }

  GameDataCommand.Session _session;
}
