package luj.game.server.internal.event.fire;

import java.util.function.Supplier;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.event.instance.EventBuilderProxy;

final class FieldImpl implements GameDataCommand.Event.Field {

  FieldImpl(EventBuilderProxy builder) {
    _builder = builder;
  }

  @Override
  public <T> GameDataCommand.Event.Field set(Supplier<T> field, T value) {
    _builder.setField(field, value);
    return this;
  }

  private final EventBuilderProxy _builder;
}
