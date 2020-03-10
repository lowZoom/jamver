package luj.game.server.internal.data.execute;

import java.util.Map;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.GameplayDataActor;

public class DataCmdExecutor {

  public DataCmdExecutor(Map<Class<?>, GameplayDataActor.CommandKit> commandMap, Class<?> cmdType) {
    _commandMap = commandMap;
    _cmdType = cmdType;
  }

  public void execute() {
    GameplayDataActor.CommandKit cmdKit = _commandMap.get(_cmdType);

    try {
      cmdKit.getCommand().onExecute(null);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private final Map<Class<?>, GameplayDataActor.CommandKit> _commandMap;

  private final Class<?> _cmdType;
}
