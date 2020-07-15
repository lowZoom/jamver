package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute;

import luj.game.server.api.cluster.ServerMessageHandler;

public class DatacmdExecMsg {

  public DatacmdExecMsg(Class<?> cmdType, Object param, ServerMessageHandler.Server remoteRef) {
    _cmdType = cmdType;
    _param = param;
    _remoteRef = remoteRef;
  }

  public Class<?> getCmdType() {
    return _cmdType;
  }

  public Object getParam() {
    return _param;
  }

  public ServerMessageHandler.Server getRemoteRef() {
    return _remoteRef;
  }

  private final Class<?> _cmdType;
  private final Object _param;

  private final ServerMessageHandler.Server _remoteRef;
}
