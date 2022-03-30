package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache;

import java.util.Map;
import luj.cache.api.container.CacheContainer;
import luj.config.api.container.ConfigContainer;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.data.id.state.DataIdPlugin;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;

public class DataActorFactory {

  public DataActorFactory(JambeanInLujcluster clusterParam, CacheContainer dataCache,
      ConfigContainer configs, Map<String, GameplayDataActor.CommandKit> commandMap,
      Map<Class<?>, GameplayDataActor.GroupKit> cmdGroupMap) {
    _clusterParam = clusterParam;
    _dataCache = dataCache;
    _configs = configs;
    _commandMap = commandMap;
    _cmdGroupMap = cmdGroupMap;
  }

  public GameplayDataActor create() {
    DataAllPlugin allPlugin = _clusterParam.getDataAllPlugin();

    DataIdPlugin idPlugin = allPlugin.getIdPlugin();
    DataIdGenState idGenState = new DataIdGenState(idPlugin.getGenerateNext());

    return new GameplayDataActor(_dataCache, _configs, idGenState, _commandMap, _cmdGroupMap,
        _clusterParam.getLujcache(), _clusterParam.getLujbean(), allPlugin,
        _clusterParam.getAppStartParam());
  }

  private final JambeanInLujcluster _clusterParam;

  private final CacheContainer _dataCache;
  private final ConfigContainer _configs;

  private final Map<String, GameplayDataActor.CommandKit> _commandMap;
  private final Map<Class<?>, GameplayDataActor.GroupKit> _cmdGroupMap;
}
