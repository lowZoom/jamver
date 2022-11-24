package luj.game.server.internal.luj.lujcluster.actor.network;

import luj.spring.anno.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnPrestart implements NetRootActor.PreStart {

  @Override
  public void onHandle(Context ctx) {
    NetRootActor self = ctx.getActorState(this);

    LOG.debug("[game]未启用网络模块");
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnPrestart.class);
}
