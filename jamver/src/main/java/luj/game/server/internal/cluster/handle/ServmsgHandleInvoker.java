package luj.game.server.internal.cluster.handle;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.plugin.JamverClusterProtoEncode;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServmsgHandleInvoker {

  public ServmsgHandleInvoker(ActorMessageHandler.Node remoteNode,
      Tellable dataRef, Map<String, ServerMessageHandler<?>> handlerMap,
      Map<Class<?>, GameplayDataActor.CommandKit> commandMap,
      JamverClusterProtoEncode encodePlugin, BeanContext lujbean, String messageKey,
      Object message) {
    _remoteNode = remoteNode;
    _dataRef = dataRef;
    _handlerMap = handlerMap;
    _commandMap = commandMap;
    _encodePlugin = encodePlugin;
    _lujbean = lujbean;
    _messageKey = messageKey;
    _message = message;
  }

  public void invoke() {
    ServerMessageHandler<?> handler = _handlerMap.get(_messageKey);
    if (handler == null) {
      LOG.info("[game]消息没有处理器：{}", _messageKey);
      return;
    }

    ServerImpl sender = new ServerImpl(_remoteNode, _encodePlugin);
    DataServiceImpl dataSvc = createDataSvc(sender);
    HandleServiceImpl handleSvc = new HandleServiceImpl(dataSvc);

    HandleContextImpl handleCtx = new HandleContextImpl(_message, sender, handleSvc);
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

  private static final Logger LOG = LoggerFactory.getLogger(ServmsgHandleInvoker.class);

  private final ActorMessageHandler.Node _remoteNode;
  private final Tellable _dataRef;

  private final Map<String, ServerMessageHandler<?>> _handlerMap;
  private final Map<Class<?>, GameplayDataActor.CommandKit> _commandMap;

  private final JamverClusterProtoEncode _encodePlugin;
  private final BeanContext _lujbean;

  private final String _messageKey;
  private final Object _message;
}
