package luj.game.server.internal.network.proto.handle.invoke;

import luj.game.server.api.net.GameProtoHandler;
import luj.game.server.internal.luj.lujcluster.actor.network.NetRootActor;

public enum ProtoHandleInvoker {
  GET;

  public void invoke(GameProtoHandler<?> handler, Object proto, Object param,
      NetRootActor actorState) throws Exception {
    var ctx = new ContextImpl();
    ctx._service = makeService(actorState);

    ctx._proto = proto;
    ctx._param = param;

    handler.onHandle(ctx);
  }

  private HandleServiceImpl makeService(NetRootActor actorState) {
    var dataSvc = new DataServiceImpl();
    dataSvc._commandMap = actorState.getCommandMap();
    dataSvc._lujbean = actorState.getLujbean();

    dataSvc._dataRef = actorState.getSiblingRef().getDataRef();
//    dataSvc._remoteRef =

    var svc = new HandleServiceImpl();
    svc._dataSvc = dataSvc;
    return svc;
  }
}
