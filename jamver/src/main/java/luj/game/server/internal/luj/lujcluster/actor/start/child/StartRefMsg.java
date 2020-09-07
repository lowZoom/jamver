package luj.game.server.internal.luj.lujcluster.actor.start.child;

import java.util.concurrent.CountDownLatch;

public class StartRefMsg {

  public StartRefMsg(TopRefCollection refCollection, CountDownLatch startLatch) {
    _refCollection = refCollection;
    _startLatch = startLatch;
  }

  public TopRefCollection getRefCollection() {
    return _refCollection;
  }

  public CountDownLatch getStartLatch() {
    return _startLatch;
  }

  private final TopRefCollection _refCollection;

  private final CountDownLatch _startLatch;
}
