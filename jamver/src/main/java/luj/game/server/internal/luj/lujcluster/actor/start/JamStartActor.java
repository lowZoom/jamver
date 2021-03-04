package luj.game.server.internal.luj.lujcluster.actor.start;

import java.util.concurrent.CountDownLatch;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

public class JamStartActor {

  public interface PreStart extends ActorPreStartHandler<JamStartActor> {
    // NOOP
  }

  public JamStartActor(CountDownLatch startLatch,
      JambeanInLujcluster startParam, TopLevelRefs refCollection) {
    _startLatch = startLatch;
    _startParam = startParam;
    _refCollection = refCollection;
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

  private final CountDownLatch _startLatch;

  private final JambeanInLujcluster _startParam;
  private final TopLevelRefs _refCollection;
}
