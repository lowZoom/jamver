package luj.game.server.internal.data.execute.service.network;

import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;

final class SessionServer implements GameDataCommand.Session {

  @Override
  public void send(Object proto) {
    _remoteRef.sendMessage(proto);
  }

  ServerMessageHandler.Server _remoteRef;
}
