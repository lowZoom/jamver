package luj.game.server.internal.luj.lujcluster.actor.gameplay.event.fire;

import luj.game.server.api.event.GameEventListener;

final class ListenerContextImpl implements GameEventListener.Context {

  ListenerContextImpl(Object event) {
    _event = event;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E> E event(GameEventListener<E> listener) {
    return (E) _event;
  }

  private final Object _event;
}
