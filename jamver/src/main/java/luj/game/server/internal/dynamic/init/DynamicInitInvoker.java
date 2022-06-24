package luj.game.server.internal.dynamic.init;

import luj.cluster.api.actor.Tellable;
import luj.game.server.api.plugin.JamverDynamicRootInit;

public class DynamicInitInvoker {

  public DynamicInitInvoker(JamverDynamicRootInit initPlugin, Tellable dataRef,
      Tellable eventRef, Tellable clusterRef, Object startParam) {
    _initPlugin = initPlugin;
    _dataRef = dataRef;
    _eventRef = eventRef;
    _clusterRef = clusterRef;
    _startParam = startParam;
  }

  public void invoke() {
    ContextImpl ctx = new ContextImpl();
    ctx._dataRef = _dataRef;
    ctx._clusterRef = _clusterRef;
    ctx._startParam = _startParam;

    _initPlugin.onInit(ctx);

    new DynamicAllRegister(ctx._registerAll, _eventRef, _dataRef, _clusterRef).register();
  }

  private final JamverDynamicRootInit _initPlugin;

  private final Tellable _dataRef;
  private final Tellable _eventRef;
  private final Tellable _clusterRef;

  private final Object _startParam;
}
