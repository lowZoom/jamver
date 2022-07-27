package luj.game.server.internal.event.listener.invoke;

import luj.game.server.api.event.GameEventListener;

final class ListenerContextImpl implements GameEventListener.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <E> E event(GameEventListener<E> listener) {
    return (E) _event;
  }

  @Override
  public GameEventListener.Service service() {
    return _service;
  }

  Object _event;

  GameEventListener.Service _service;
}
