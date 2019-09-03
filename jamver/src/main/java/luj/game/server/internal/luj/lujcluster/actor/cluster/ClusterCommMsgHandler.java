package luj.game.server.internal.luj.lujcluster.actor.cluster;

import luj.cluster.api.actor.ActorMessageHandler;

public interface ClusterCommMsgHandler<M> extends ActorMessageHandler<ClusterCommActor, M> {
  // NOOP
}
