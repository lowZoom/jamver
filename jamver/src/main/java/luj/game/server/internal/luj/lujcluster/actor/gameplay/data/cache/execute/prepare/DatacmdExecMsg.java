package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.prepare;

import luj.game.server.api.cluster.ServerMessageHandler;

public class DatacmdExecMsg {

  public DatacmdExecMsg(String cmdType, Object param, ServerMessageHandler.Server remoteRef) {
    _cmdType = cmdType;
    _param = param;
    _remoteRef = remoteRef;
  }

  public String getCmdType() {
    return _cmdType;
  }

  public Object getParam() {
    return _param;
  }

  public ServerMessageHandler.Server getRemoteRef() {
    return _remoteRef;
  }

  private final String _cmdType;
  private final Object _param;

  private final ServerMessageHandler.Server _remoteRef;
}
