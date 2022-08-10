package luj.game.server.internal.luj.lujcluster.actor.start;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import luj.ava.spring.Internal;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.dynamic.init.DynamicInitInvoker;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
import luj.game.server.internal.luj.lujcluster.actor.start.trigger.TriggerStartMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnPrestart implements JamStartActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    JamStartActor self = ctx.getActorState(this);
    Collection<GameStartListener> dynamicList = initDynamic(self);

    ctx.getActor().tell(new TriggerStartMsg(ImmutableList.<GameStartListener>builder()
        .addAll(self.getStartParam().getStartListenerList())
        .addAll(dynamicList)
        .build()));
  }

  private Collection<GameStartListener> initDynamic(JamStartActor self) {
    JamverDynamicRootInit initPlugin = self.getDynamicInit();
    if (initPlugin == null) {
      LOG.debug("[game]未启用热更模块");
      return ImmutableList.of();
    }

    TopLevelRefs allRef = self.getRefCollection();
    Object appParam = self.getStartParam().getAppStartParam();

    return new DynamicInitInvoker(initPlugin, allRef.getDataRef(), allRef.getEventRef(),
        allRef.getClusterRef(), appParam).invoke();
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnPrestart.class);
}
