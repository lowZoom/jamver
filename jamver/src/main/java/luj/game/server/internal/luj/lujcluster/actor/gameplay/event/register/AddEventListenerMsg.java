package luj.game.server.internal.luj.lujcluster.actor.gameplay.event.register;

import java.util.Collection;
import luj.game.server.api.event.GameEventListener;

public class AddEventListenerMsg {

  public AddEventListenerMsg(Collection<GameEventListener<?>> listeners) {
    _listeners = listeners;
  }

  public Collection<GameEventListener<?>> getListeners() {
    return _listeners;
  }

  private final Collection<GameEventListener<?>> _listeners;
}
