package luj.game.server.internal.luj.lujcluster.actor.start.child;

import luj.cluster.api.actor.Tellable;

public class TopRefCollection {

  public TopRefCollection(Tellable dataRef, Tellable eventRef, Tellable clusterRef) {
    _dataRef = dataRef;
    _eventRef = eventRef;
    _clusterRef = clusterRef;
  }

  public Tellable getDataRef() {
    return _dataRef;
  }

  public Tellable getEventRef() {
    return _eventRef;
  }

  public Tellable getClusterRef() {
    return _clusterRef;
  }

  private final Tellable _dataRef;
  private final Tellable _eventRef;
  private final Tellable _clusterRef;
}
