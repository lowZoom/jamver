package luj.game.server.internal.network.proto.handle.invoke;

import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;

public enum ProtoHandleInvoker {
  GET;

  public void invoke(GameProtoHandler<?> handler, Object proto,
      NetRootActor actorState) throws Exception {
    ContextImpl ctx = new ContextImpl();
    ctx._service = makeService(actorState);
    ctx._proto = proto;

    handler.onHandle(ctx);
  }

  private HandleServiceImpl makeService(NetRootActor actorState) {
    HandleServiceImpl svc = new HandleServiceImpl();

    DataServiceImpl dataSvc = new DataServiceImpl();
    svc._dataSvc = dataSvc;

    dataSvc._commandMap = actorState.getCommandMap();
    dataSvc._lujbean = actorState.getLujbean();

    dataSvc._dataRef = actorState.getSiblingRef().getDataRef();
//    dataSvc._remoteRef =

    return svc;
  }
}
