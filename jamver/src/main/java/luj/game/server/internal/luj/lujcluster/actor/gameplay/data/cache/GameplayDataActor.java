package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import luj.bean.api.BeanContext;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.config.api.container.ConfigContainer;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.data.command.queue.DataCommandRequest;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.schedule.state.ScheduleMap;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
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

    Class<?> getParamType();

    GameDataLoad<?, ?> getLoader();

    Class<?> getLoadResultType();

    Logger getLogger();

    /**
     * @return 该cmd所处的map
     */
    Map<String, CommandKit> getParentMap();
  }

  public interface GroupKit {

    GameDataCommandGroup getGroup();

    Class<?> getGroupType();
  }

  public GameplayDataActor(CacheContainer dataCache, ConfigContainer configs,
      DataIdGenState idGenState, Map<String, CommandKit> commandMap,
      Map<Class<?>, GroupKit> cmdGroupMap, CacheSession lujcache, BeanContext lujbean,
      DataAllPlugin allPlugin, Object startParam) {
    _dataCache = dataCache;
    _configs = configs;
    _idGenState = idGenState;
    _commandMap = commandMap;
    _cmdGroupMap = cmdGroupMap;
    _lujcache = lujcache;
    _lujbean = lujbean;
    _allPlugin = allPlugin;
    _startParam = startParam;
  }

  public Object getPluginState() {
    return _pluginState;
  }

  public void setPluginState(Object pluginState) {
    _pluginState = pluginState;
  }

  public DataIdGenState getIdGenState() {
    return _idGenState;
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

  public TopLevelRefs getSiblingRef() {
    return _siblingRef;
  }

  public void setSiblingRef(TopLevelRefs siblingRef) {
    _siblingRef = siblingRef;
  }

  public CacheContainer getDataCache() {
    return _dataCache;
  }

  public ConfigContainer getConfigs() {
    return _configs;
  }

  public Queue<DataCommandRequest> getCommandQueue() {
    return _commandQueue;
  }

  public ScheduleMap getScheduleMap() {
    return _scheduleMap;
  }

  public Map<String, CommandKit> getCommandMap() {
    return _commandMap;
  }

  public Map<Class<?>, GroupKit> getCmdGroupMap() {
    return _cmdGroupMap;
  }

  public CacheSession getLujcache() {
    return _lujcache;
  }

  public BeanContext getLujbean() {
    return _lujbean;
  }

  public DataAllPlugin getAllPlugin() {
    return _allPlugin;
  }

  public Object getStartParam() {
    return _startParam;
  }

  private Object _pluginState;

  private ActorPreStartHandler.Actor _loadRef;
  private ActorPreStartHandler.Actor _saveRef;

  private TopLevelRefs _siblingRef;

  private final CacheContainer _dataCache;
  private final ConfigContainer _configs;

  private final DataIdGenState _idGenState;
  private final Queue<DataCommandRequest> _commandQueue = new LinkedList<>();
  private final ScheduleMap _scheduleMap = new ScheduleMap();

  /////////////////////////

  private final Map<String, CommandKit> _commandMap;
  private final Map<Class<?>, GroupKit> _cmdGroupMap;

  private final CacheSession _lujcache;
  private final BeanContext _lujbean;

  private final DataAllPlugin _allPlugin;
  private final Object _startParam;
}
