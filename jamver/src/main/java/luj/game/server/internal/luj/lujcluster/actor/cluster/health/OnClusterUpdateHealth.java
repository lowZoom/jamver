package luj.game.server.internal.luj.lujcluster.actor.cluster.health;

import luj.ava.spring.Internal;
import luj.game.server.internal.cluster.health.HealthUpdateFirer;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;

@Internal
final class OnClusterUpdateHealth implements ClusterCommActor.Handler<ClusterUpdateHealthMsg> {

  @Override
  public void onHandle(Context ctx) throws Exception {
    ClusterCommActor self = ctx.getActorState(this);
    ClusterUpdateHealthMsg msg = ctx.getMessage(this);

    new HealthUpdateFirer(self.getHealthListeners(), msg.getId(),
        msg.getTags(), msg.isHealthy(), self.getCommandMap()).fire();
  }
}
