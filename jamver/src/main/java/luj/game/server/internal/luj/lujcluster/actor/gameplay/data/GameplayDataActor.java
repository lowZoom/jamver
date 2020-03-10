package luj.game.server.internal.luj.lujcluster.actor.gameplay.data;

import java.util.Map;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import org.slf4j.Logger;

public class GameplayDataActor {

  public interface Handler<M> extends ActorMessageHandler<GameplayDataActor, M> {
    // NOOP
  }

  public interface CommandKit {

    GameDataCommand<?, ?> getCommand();

    GameDataLoad<?, ?> getLoad();

    Logger getLogger();
  }

  public GameplayDataActor(Map<Class<?>, CommandKit> commandMap) {
    _commandMap = commandMap;
  }

  public Map<Class<?>, CommandKit> getCommandMap() {
    return _commandMap;
  }

  private final Map<Class<?>, CommandKit> _commandMap;
}
