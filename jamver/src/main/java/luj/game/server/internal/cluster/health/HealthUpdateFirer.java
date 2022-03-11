package luj.game.server.internal.cluster.health;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Map;
import luj.game.server.api.cluster.ServerHealthListener;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class HealthUpdateFirer {

  public HealthUpdateFirer(List<ServerHealthListener> listenerList, String id, List<String> tags,
      boolean healthy, Map<String, GameplayDataActor.CommandKit> commandMap) {
    _listenerList = listenerList;
    _id = id;
    _tags = tags;
    _healthy = healthy;
    _commandMap = commandMap;
  }

  public void fire() throws Exception {
    ServerImpl remote = new ServerImpl();
    remote._id = _id;
    remote._tags = ImmutableSet.copyOf(_tags);
    remote._healthy = _healthy;

    HealthContextImpl ctx = new HealthContextImpl();
    ctx._server = remote;
    ctx._service = makeService();

    for (ServerHealthListener l : _listenerList) {
      l.onHandle(ctx);
    }
  }

  private HealthServiceImpl makeService() {
    DataServiceImpl dataSvc = new DataServiceImpl();
    dataSvc._commandMap = _commandMap;

    HealthServiceImpl service = new HealthServiceImpl();
    service._data = dataSvc;

    return service;
  }

  private final List<ServerHealthListener> _listenerList;

  private final String _id;
  private final List<String> _tags;

  private final boolean _healthy;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;
}
