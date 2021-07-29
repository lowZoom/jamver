package luj.game.server.internal.luj.lujcluster.actor.dynamic;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

/**
 * 动态代码模块
 */
public class DynamicRootActor {

  public interface Handler<M> extends ActorMessageHandler<DynamicRootActor, M> {
    // NOOP
  }

  public static DynamicRootActor create(JambeanInLujcluster clusterParam) {
    return new DynamicRootActor(clusterParam.getDynamicInitPlugin(),
        clusterParam.getAppStartParam());
  }

  public DynamicRootActor(JamverDynamicRootInit initPlugin, Object startParam) {
    _initPlugin = initPlugin;
    _startParam = startParam;
  }

  public TopLevelRefs getSiblingRef() {
    return _siblingRef;
  }

  public void setSiblingRef(TopLevelRefs siblingRef) {
    _siblingRef = siblingRef;
  }

  public JamverDynamicRootInit getInitPlugin() {
    return _initPlugin;
  }

  public Object getStartParam() {
    return _startParam;
  }

  private TopLevelRefs _siblingRef;

  private final JamverDynamicRootInit _initPlugin;
  private final Object _startParam;
}
