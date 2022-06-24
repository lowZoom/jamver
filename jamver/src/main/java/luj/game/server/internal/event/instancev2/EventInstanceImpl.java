package luj.game.server.internal.event.instancev2;

import java.util.function.Supplier;
import luj.bean.api.bean.Bean;
import luj.game.server.api.data.GameDataCommand;

final class EventInstanceImpl<T> implements GameDataCommand.Event.Instance,
    GameDataCommand.Event.Field<T> {

  @SuppressWarnings("unchecked")
  @Override
  public <V> GameDataCommand.Event.Field<V> set(Supplier<V> field) {
    _field = (Supplier<T>) field;
    return (GameDataCommand.Event.Field<V>) this;
  }

  @SuppressWarnings("DollarSignInName")
  @Override
  public GameDataCommand.Event.Instance $(T value) {
    _event.setField(_field, value);
    return this;
  }

  Bean<?> _event;

  Supplier<T> _field;
}
