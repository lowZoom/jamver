package luj.game.server.internal.luj.lujcluster.actor.gameplay.event;

import luj.cluster.api.actor.ActorMessageHandler;

public interface EventActorMsgHandler<M> extends ActorMessageHandler<GameplayEventActor, M> {
  // NOOP
}
