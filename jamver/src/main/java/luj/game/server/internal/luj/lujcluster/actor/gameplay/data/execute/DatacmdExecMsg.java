package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.execute;

public class DatacmdExecMsg {

  public DatacmdExecMsg(Class<?> cmdType, Object param) {
    _cmdType = cmdType;
    _param = param;
  }

  public Class<?> getCmdType() {
    return _cmdType;
  }

  public Object getParam() {
    return _param;
  }

  private final Class<?> _cmdType;

  private final Object _param;
}
