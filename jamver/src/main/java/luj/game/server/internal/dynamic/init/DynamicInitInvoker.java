package luj.game.server.internal.dynamic.init;

import java.util.Collection;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

public class DynamicInitInvoker {

  public DynamicInitInvoker(JamverDynamicRootInit initPlugin, TopLevelRefs allRef,
      Object startParam) {
    _initPlugin = initPlugin;
    _allRef = allRef;
    _startParam = startParam;
  }

  public Collection<GameStartListener> invoke() {
    ContextImpl ctx = new ContextImpl();
    ctx._startParam = _startParam;
    _initPlugin.onInit(ctx);

    return new DynamicAllRegister(ctx._registerAll, _allRef.getDataRef(),
        _allRef.getEventRef(), _allRef.getNetworkRef(), _allRef.getClusterRef()).register();
  }

  private final JamverDynamicRootInit _initPlugin;

  private final TopLevelRefs _allRef;
  private final Object _startParam;
}
