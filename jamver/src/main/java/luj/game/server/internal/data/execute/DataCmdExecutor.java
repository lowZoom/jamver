package luj.game.server.internal.data.execute;

import java.util.Map;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.GameplayDataActor;

public class DataCmdExecutor {

  public DataCmdExecutor(Map<Class<?>, GameplayDataActor.CommandKit> commandMap,
      Class<?> cmdType, Object param) {
    _commandMap = commandMap;
    _cmdType = cmdType;
    _param = param;
  }

  public void execute() {
    GameplayDataActor.CommandKit cmdKit = _commandMap.get(_cmdType);

    //TODO: 增加临时数据读取逻辑

    try {
      cmdKit.getCommand().onExecute(new CommandContextImpl(_param));
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private final Map<Class<?>, GameplayDataActor.CommandKit> _commandMap;
  private final Class<?> _cmdType;

  private final Object _param;
}
