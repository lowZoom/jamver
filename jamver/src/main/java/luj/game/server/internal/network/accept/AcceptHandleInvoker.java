package luj.game.server.internal.network.accept;

import luj.game.server.api.net.NetAcceptHandler;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;
import luj.game.server.internal.luj.lujcluster.actor.network.accept.AcceptConnMsg;

public enum AcceptHandleInvoker {
  GET;

  public void invoke(NetRootActor actorState, AcceptConnMsg msg) {
    ContextImpl ctx = new ContextImpl();
    ctx._service = makeService(actorState);

    ConnectionImpl conn = new ConnectionImpl();
    ctx._conn = conn;

    conn._id = msg.getConnectionId();
    conn._bindAddr = msg.getBindAddr();

    NetAcceptHandler handler = actorState.getAcceptHandler();
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
