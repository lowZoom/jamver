package luj.game.server.internal.cluster.handle;

import java.util.Map;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.node.NodeStartListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServmsgHandleInvoker {

  public ServmsgHandleInvoker(ActorMessageHandler.Node remoteNode, NodeStartListener.Actor dataRef,
      Map<String, ServerMessageHandler<?>> handlerMap, String messageKey, Object message) {
    _remoteNode = remoteNode;
    _dataRef = dataRef;
    _handlerMap = handlerMap;
    _messageKey = messageKey;
    _message = message;
  }

  public void invoke() {
    ServerMessageHandler<?> handler = _handlerMap.get(_messageKey);
    if (handler == null) {
      LOG.info("分布式消息没有处理器：{}", _messageKey);
      return;
    }

    ServerImpl sender = new ServerImpl(_remoteNode);

    DataServiceImpl dataSvc = new DataServiceImpl(_dataRef);
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

  private static final Logger LOG = LoggerFactory.getLogger(ServmsgHandleInvoker.class);

  private final ActorMessageHandler.Node _remoteNode;

  private final NodeStartListener.Actor _dataRef;
  private final Map<String, ServerMessageHandler<?>> _handlerMap;

  private final String _messageKey;
  private final Object _message;
}
