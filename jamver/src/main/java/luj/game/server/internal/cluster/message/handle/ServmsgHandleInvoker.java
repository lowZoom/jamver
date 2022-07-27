package luj.game.server.internal.cluster.message.handle;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServmsgHandleInvoker {

  public ServmsgHandleInvoker(String messageKey, Object message,
      Map<String, ServerMessageHandler<?>> handlerMap,
      Map<String, GameplayDataActor.CommandKit> commandMap, ActorMessageHandler.Node remoteNode,
      Tellable clusterRef, Tellable dataRef, BeanContext lujbean) {
    _messageKey = messageKey;
    _message = message;
    _handlerMap = handlerMap;
    _commandMap = commandMap;
    _remoteNode = remoteNode;
    _clusterRef = clusterRef;
    _dataRef = dataRef;
    _lujbean = lujbean;
  }

  public void invoke() {
    ServerMessageHandler<?> handler = _handlerMap.get(_messageKey);
    if (handler == null) {
      LOG.info("[game]消息没有处理器：{}", _messageKey);
      return;
    }

    HandleServiceImpl handleSvc = new HandleServiceImpl();
    ServerImpl sender = makeRemoteServer();
    handleSvc._dataSvc = createDataSvc(sender);

    HandleContextImpl handleCtx = new HandleContextImpl();
    handleCtx._message = _message;
    handleCtx._remoteServer = sender;
    handleCtx._service = handleSvc;

    try {
      handler.onHandle(handleCtx);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private DataServiceImpl createDataSvc(ServerImpl sender) {
    DataServiceImpl svc = new DataServiceImpl();
    svc._commandMap = _commandMap;
    svc._lujbean = _lujbean;
    svc._dataRef = _dataRef;
    svc._remoteRef = sender;
    return svc;
  }

  private ServerImpl makeRemoteServer() {
    ServerImpl sender = new ServerImpl();
    sender._clusterRef = _clusterRef;
    sender._remoteNode = _remoteNode;
    return sender;
  }

  private static final Logger LOG = LoggerFactory.getLogger(ServmsgHandleInvoker.class);

  private final String _messageKey;
  private final Object _message;

  private final Map<String, ServerMessageHandler<?>> _handlerMap;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final ActorMessageHandler.Node _remoteNode;
  private final Tellable _clusterRef;
  private final Tellable _dataRef;

  private final BeanContext _lujbean;
}
