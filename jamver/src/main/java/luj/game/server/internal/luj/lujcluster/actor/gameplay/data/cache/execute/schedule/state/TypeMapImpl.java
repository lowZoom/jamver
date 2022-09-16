package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.schedule.state;

import akka.actor.Cancellable;
import java.util.HashMap;
import java.util.Map;

final class TypeMapImpl implements ScheduleMap.TypeMap {

  @Override
  public Cancellable put(Comparable<?> id, Cancellable sched) {
    if (_type != null) {
      _parentMap.put(_type, this);
      _type = null;
    }
    return _schedMap.put(id, sched);
  }

  Map<String, ScheduleMap.TypeMap> _parentMap;
  String _type;

  private final Map<Comparable<?>, Cancellable> _schedMap = new HashMap<>();
}
