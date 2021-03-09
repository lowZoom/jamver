package luj.game.server.internal.network.accept;

import luj.cluster.api.actor.Tellable;
import luj.game.server.api.net.GameAcceptHandler;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.net.api.server.ConnectionAcceptInitializer;

public enum AcceptHandleInvoker {
  GET;

  public void invoke(NetRootActor actorState, Tellable actorRef, Integer connId,
      ConnectionAcceptInitializer.Connection conn, ConnectionAcceptInitializer.Address bindAddr) {
    ContextImpl ctx = new ContextImpl();
    ctx._bindAddr = bindAddr;
    ctx._service = makeService(actorState);

    ConnectionImpl ctxConn = new ConnectionImpl();
    ctx._conn = ctxConn;

    ctxConn._connId = connId;
    ctxConn._netRef = actorRef;
    ctxConn._conn = conn;

    GameAcceptHandler handler = actorState.getAcceptHandler();
    handler.onHandle(ctx);
  }

  private ServiceImpl makeService(NetRootActor actorState) {
    ServiceImpl svc = new ServiceImpl();

    ServiceData dataSvc = new ServiceData();
    svc._dataSvc = dataSvc;

    dataSvc._commandMap = actorState.getCommandMap();
    dataSvc._lujbean = actorState.getLujbean();

    dataSvc._dataRef = actorState.getSiblingRef().getDataRef();
//    dataSvc._remoteRef =

    return svc;
  }
}
