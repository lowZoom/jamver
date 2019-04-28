package luj.game.server.internal.luj.lujcluster.actor.gameplay.event.fire;

import luj.game.server.api.event.GameEventListener;

final class ListenerContextImpl implements GameEventListener.Context {

  ListenerContextImpl(Object event, GameEventListener.Service service) {
    _event = event;
    _service = service;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E> E event(GameEventListener<E> listener) {
    return (E) _event;
  }

  @Override
  public GameEventListener.Service service() {
    return _service;
  }

  private final Object _event;

  private final GameEventListener.Service _service;
}
