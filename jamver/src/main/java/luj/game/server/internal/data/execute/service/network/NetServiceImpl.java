package luj.game.server.internal.data.execute.service.network;

import luj.game.server.api.data.GameDataCommand;

final class NetServiceImpl implements GameDataCommand.Network {

  @Override
  public GameDataCommand.Session session() {
    return _session;
  }

  @Override
  public GameDataCommand.Session session(Comparable<?> sessionId) {
    throw new UnsupportedOperationException("session");
  }

  @Override
  public GameDataCommand.Server server() {
    throw new UnsupportedOperationException("server");
  }

  @Override
  public GameDataCommand.Http http() {
    throw new UnsupportedOperationException("http");
  }

  GameDataCommand.Session _session;
}
