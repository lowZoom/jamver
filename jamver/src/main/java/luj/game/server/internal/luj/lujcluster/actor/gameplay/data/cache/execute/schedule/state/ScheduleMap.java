package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.schedule.state;

import akka.actor.Cancellable;
import java.util.HashMap;
import java.util.Map;

public class ScheduleMap {

  public interface TypeMap {

    Cancellable put(Comparable<?> id, Cancellable sched);
  }

  public TypeMap get(String cmdType) {
    return _byType.computeIfAbsent(cmdType, this::initNewType);
  }

  private TypeMap initNewType(String typeName) {
    TypeMapImpl typeMap = new TypeMapImpl();
    typeMap._parentMap = _byType;
    typeMap._type = typeName;
    return typeMap;
  }

  private final Map<String, TypeMap> _byType = new HashMap<>();
}
