package luj.game.server.internal.network.disconnect;

import luj.cluster.api.actor.Tellable;
import luj.game.server.api.net.GameDisconnectHandler;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;

public enum DisconnectHandleInvoker {
  GET;

  public void invoke(NetRootActor actorState, Tellable actorRef, Integer connId) {
    ContextImpl ctx = new ContextImpl();
    ctx._service = makeService(actorState);

    ConnectionImpl conn = new ConnectionImpl();
    ctx._conn = conn;
    conn._id = connId;

    GameDisconnectHandler handler = actorState.getDisconnectHandler();
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
