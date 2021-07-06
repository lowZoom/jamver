package luj.game.server.internal.luj.lujcluster.actor.start;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

public class JamStartActor {

  public interface PreStart extends ActorPreStartHandler<JamStartActor> {
    // NOOP
  }

  public JamStartActor(CountDownLatch startLatch, JambeanInLujcluster startParam,
      TopLevelRefs refCollection, Map<String, GameplayDataActor.CommandKit> commandMap) {
    _startLatch = startLatch;
    _startParam = startParam;
    _refCollection = refCollection;
    _commandMap = commandMap;
  }

  public CountDownLatch getStartLatch() {
    return _startLatch;
  }

  public JambeanInLujcluster getStartParam() {
    return _startParam;
  }

  public TopLevelRefs getRefCollection() {
    return _refCollection;
  }

  public Map<String, GameplayDataActor.CommandKit> getCommandMap() {
    return _commandMap;
  }

  private final CountDownLatch _startLatch;

  private final JambeanInLujcluster _startParam;
  private final TopLevelRefs _refCollection;

  private final Map<String, GameplayDataActor.CommandKit> _commandMap;
}
