package luj.game.server.internal.luj.lujcluster.actor.gameplay.data;

import java.util.Map;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.dataload.DataLoadPlugin;
import org.slf4j.Logger;

public class GameplayDataActor {

  public interface Handler<M> extends ActorMessageHandler<GameplayDataActor, M> {
    // NOOP
  }

  public interface PreStart extends ActorPreStartHandler<GameplayDataActor> {
    // NOOP
  }

  public interface CommandKit {

    GameDataCommand<?, ?> getCommand();

    GameDataLoad<?, ?> getLoader();

    Class<?> getLoadResultType();

    Logger getLogger();
  }

  public GameplayDataActor(CacheContainer dataCache, CacheSession lujcache,
      Map<Class<?>, CommandKit> commandMap, DataLoadPlugin loadPlugin) {
    _dataCache = dataCache;
    _lujcache = lujcache;
    _commandMap = commandMap;
    _loadPlugin = loadPlugin;
  }

  public ActorPreStartHandler.Actor getLoadRef() {
    return _loadRef;
  }

  public void setLoadRef(ActorPreStartHandler.Actor loadRef) {
    _loadRef = loadRef;
  }

  public CacheContainer getDataCache() {
    return _dataCache;
  }

  public Map<Class<?>, CommandKit> getCommandMap() {
    return _commandMap;
  }

  public CacheSession getLujcache() {
    return _lujcache;
  }

  public DataLoadPlugin getLoadPlugin() {
    return _loadPlugin;
  }

  private ActorPreStartHandler.Actor _loadRef;

  private final CacheContainer _dataCache;

  private final Map<Class<?>, CommandKit> _commandMap;
  private final CacheSession _lujcache;

  private final DataLoadPlugin _loadPlugin;
}
