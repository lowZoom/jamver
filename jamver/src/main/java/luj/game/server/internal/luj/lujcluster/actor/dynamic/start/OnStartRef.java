package luj.game.server.internal.luj.lujcluster.actor.dynamic.start;

import luj.ava.spring.Internal;
import luj.game.server.api.plugin.JamverDynamicRootInit;
import luj.game.server.internal.dynamic.init.DynamicInitInvoker;
import luj.game.server.internal.luj.lujcluster.actor.dynamic.DynamicRootActor;
import luj.game.server.internal.luj.lujcluster.actor.start.child.StartRefMsg;
import luj.game.server.internal.luj.lujcluster.actor.start.child.TopLevelRefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnStartRef implements DynamicRootActor.Handler<StartRefMsg> {

  @Override
  public void onHandle(Context ctx) {
    DynamicRootActor self = ctx.getActorState(this);
    StartRefMsg msg = ctx.getMessage(this);

    self.setSiblingRef(msg.getRefCollection());
    startActor(self);

    msg.getStartLatch().countDown();
  }

  private void startActor(DynamicRootActor self) {
    JamverDynamicRootInit initPlugin = self.getInitPlugin();
    if (initPlugin == null) {
      LOG.debug("[game]未启用热更模块");
      return;
    }

    TopLevelRefs sibling = self.getSiblingRef();

    new DynamicInitInvoker(initPlugin,
        sibling.getDataRef(), sibling.getEventRef(), sibling.getClusterRef(),
        self.getStartParam()).invoke();
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnStartRef.class);
}
