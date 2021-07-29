package luj.game.server.internal.luj.lujcluster.actor.start.child;

import luj.cluster.api.actor.Tellable;

public class TopLevelRefs {

  public TopLevelRefs(Tellable dataRef, Tellable eventRef, Tellable clusterRef,
      Tellable networkRef, Tellable dynamicRef) {
    _dataRef = dataRef;
    _eventRef = eventRef;
    _clusterRef = clusterRef;
    _networkRef = networkRef;
    _dynamicRef = dynamicRef;
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

  public Tellable getNetworkRef() {
    return _networkRef;
  }

  public Tellable getDynamicRef() {
    return _dynamicRef;
  }

  private final Tellable _dataRef;
  private final Tellable _eventRef;

  private final Tellable _clusterRef;
  private final Tellable _networkRef;

  private final Tellable _dynamicRef;
}
