package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache;

import java.util.Map;
import java.util.Queue;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.api.plugin.JamverDataRootInit;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSavePlugin;
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

    Class<?> getCommandType();

    GameDataLoad<?, ?> getLoader();

    Class<?> getLoadResultType();

    Logger getLogger();
  }

  public GameplayDataActor(CacheContainer dataCache, Queue<DataCommandRequest> commandQueue,
      CacheSession lujcache, Map<Class<?>, CommandKit> commandMap, JamverDataRootInit initPlugin,
      DataLoadPlugin loadPlugin, DataSavePlugin savePlugin) {
    _dataCache = dataCache;
    _commandQueue = commandQueue;
    _lujcache = lujcache;
    _commandMap = commandMap;
    _initPlugin = initPlugin;
    _loadPlugin = loadPlugin;
    _savePlugin = savePlugin;
  }

  public Object getPluginState() {
    return _pluginState;
  }

  public void setPluginState(Object pluginState) {
    _pluginState = pluginState;
  }

  public ActorPreStartHandler.Actor getLoadRef() {
    return _loadRef;
  }

  public void setLoadRef(ActorPreStartHandler.Actor loadRef) {
    _loadRef = loadRef;
  }

  public ActorPreStartHandler.Actor getSaveRef() {
    return _saveRef;
  }

  public void setSaveRef(ActorPreStartHandler.Actor saveRef) {
    _saveRef = saveRef;
  }

  public CacheContainer getDataCache() {
    return _dataCache;
  }

  public Queue<DataCommandRequest> getCommandQueue() {
    return _commandQueue;
  }

  public Map<Class<?>, CommandKit> getCommandMap() {
    return _commandMap;
  }

  public CacheSession getLujcache() {
    return _lujcache;
  }

  public JamverDataRootInit getInitPlugin() {
    return _initPlugin;
  }

  public DataLoadPlugin getLoadPlugin() {
    return _loadPlugin;
  }

  public DataSavePlugin getSavePlugin() {
    return _savePlugin;
  }

  private Object _pluginState;

  private ActorPreStartHandler.Actor _loadRef;
  private ActorPreStartHandler.Actor _saveRef;

  private final CacheContainer _dataCache;
  private final Queue<DataCommandRequest> _commandQueue;

  /////////////////////////

  private final Map<Class<?>, CommandKit> _commandMap;
  private final CacheSession _lujcache;

  private final JamverDataRootInit _initPlugin;

  private final DataLoadPlugin _loadPlugin;
  private final DataSavePlugin _savePlugin;
}
