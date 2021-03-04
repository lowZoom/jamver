package luj.game.server.internal.luj.lujcluster.actor.start.child;

import java.util.concurrent.CountDownLatch;

public class StartRefMsg {

  public StartRefMsg(TopLevelRefs refCollection, CountDownLatch startLatch) {
    _refCollection = refCollection;
    _startLatch = startLatch;
  }

  public TopLevelRefs getRefCollection() {
    return _refCollection;
  }

  public CountDownLatch getStartLatch() {
    return _startLatch;
  }

  private final TopLevelRefs _refCollection;

  private final CountDownLatch _startLatch;
}
