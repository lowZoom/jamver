package luj.game.server.internal.luj.lujcluster.actor.cluster.register.dynamic.join;

import java.util.Collection;
import luj.ava.spring.Internal;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.internal.luj.lujcluster.actor.cluster.ClusterCommActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnAddJoinListener implements ClusterCommActor.Handler<AddJoinListenerMsg> {

  @Override
  public void onHandle(Context ctx) {
    ClusterCommActor self = ctx.getActorState(this);
    AddJoinListenerMsg msg = ctx.getMessage(this);

    Collection<ServerJoinListener> addList = msg.getJoinListener();
    LOG.debug("[game]新增ServerJoinListener，数量：{}", addList.size());

    self.getJoinListeners().addAll(addList);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnAddJoinListener.class);
}
