package luj.game.server.internal.dynamic.init;

import luj.cluster.api.actor.Tellable;
import luj.game.server.api.plugin.JamverDynamicRootInit;

public class DynamicInitInvoker {

  public DynamicInitInvoker(JamverDynamicRootInit initPlugin, Tellable dataRef,
      Tellable clusterRef, Object startParam) {
    _initPlugin = initPlugin;
    _dataRef = dataRef;
    _clusterRef = clusterRef;
    _startParam = startParam;
  }

  public void invoke() {
    ContextImpl ctx = new ContextImpl();
    ctx._dataRef = _dataRef;
    ctx._clusterRef = _clusterRef;
    ctx._startParam = _startParam;

    _initPlugin.onInit(ctx);
  }

  private final JamverDynamicRootInit _initPlugin;

  private final Tellable _dataRef;
  private final Tellable _clusterRef;

  private final Object _startParam;
}
