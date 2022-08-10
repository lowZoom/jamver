package luj.game.server.internal.luj.lujcluster.actor.start;

import java.util.Map;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.luj.lujcluster.JamPluginCollect;
import luj.game.server.internal.luj.lujcluster.JambeanInLujcluster;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;

public class JamStartActor {

  public interface PreStart extends ActorPreStartHandler<JamStartActor> {
    // NOOP
  }

  public interface Handler<M> extends ActorMessageHandler<JamStartActor, M> {
    // NOOP
  }

  public static JamStartActor create(JambeanInLujcluster clusterParam,
      TopLevelRefs refCollection, Map<String, GameplayDataActor.CommandKit> commandMap) {
    JamPluginCollect plugin = clusterParam.getAllPlugin();
    return new JamStartActor(plugin.getDynamicInit(), clusterParam, refCollection, commandMap);
  }

  public JamStartActor(JamverDynamicRootInit dynamicInit, JambeanInLujcluster startParam,
      TopLevelRefs refCollection, Map<String, GameplayDataActor.CommandKit> commandMap) {
    _dynamicInit = dynamicInit;
    _startParam = startParam;
    _refCollection = refCollection;
    _commandMap = commandMap;
  }

  public JamverDynamicRootInit getDynamicInit() {
    return _dynamicInit;
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

  private final JamverDynamicRootInit _dynamicInit;
  private final JambeanInLujcluster _startParam;

  private final TopLevelRefs _refCollection;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;
}
