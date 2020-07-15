package luj.game.server.internal.data.execute.service.network;

import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;

public class NetServiceFactory {

  public NetServiceFactory(ServerMessageHandler.Server remoteRef) {
    _remoteRef = remoteRef;
  }

  public GameDataCommand.Network create() {
    NetServiceImpl svc = new NetServiceImpl();
    svc._session = makeSession();
    return svc;
  }

  private GameDataCommand.Session makeSession() {
    //TODO: 由客户端网络触发过来

    SessionServer sessionServer = new SessionServer();
    sessionServer._remoteRef = _remoteRef;
    return sessionServer;
  }

  private final ServerMessageHandler.Server _remoteRef;
}
