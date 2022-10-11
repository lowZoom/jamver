package luj.game.server.internal.luj.lujcluster.actor.start;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

public class JamStartActor {

  public interface PreStart extends ActorPreStartHandler<JamStartActor> {
    // NOOP
  }

  public interface Handler<M> extends ActorMessageHandler<JamStartActor, M> {
    // NOOP
  }

  public static JamStartActor create(Collection<GameStartListener> dynamicStart,
      JambeanInLujcluster clusterParam, TopLevelRefs refCollection,
      Map<String, GameplayDataActor.CommandKit> commandMap) {
    return new JamStartActor(ImmutableList.copyOf(dynamicStart), clusterParam,
        refCollection.getDataRef(), commandMap);
  }

  public JamStartActor(List<GameStartListener> dynamicStart, JambeanInLujcluster startParam,
      Tellable dataRef, Map<String, GameplayDataActor.CommandKit> commandMap) {
    _dynamicStart = dynamicStart;
    _startParam = startParam;
    _dataRef = dataRef;
    _commandMap = commandMap;
  }

  public List<GameStartListener> getDynamicStart() {
    return _dynamicStart;
  }

  public JambeanInLujcluster getStartParam() {
    return _startParam;
  }

  public Tellable getDataRef() {
    return _dataRef;
  }

  public Map<String, GameplayDataActor.CommandKit> getCommandMap() {
    return _commandMap;
  }

  private final List<GameStartListener> _dynamicStart;

  private final JambeanInLujcluster _startParam;
  private final Tellable _dataRef;

  private final Map<String, GameplayDataActor.CommandKit> _commandMap;
}
