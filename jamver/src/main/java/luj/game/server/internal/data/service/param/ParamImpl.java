package luj.game.server.internal.data.service.param;

import java.util.function.Supplier;
import luj.bean.api.bean.Bean;
import luj.game.server.api.data.service.CommandService;

final class ParamImpl<T> implements CommandService.Param, CommandService.Field<T> {

  @SuppressWarnings("unchecked")
  @Override
  public <V> CommandService.Field<V> set(Supplier<V> field) {
    _field = (Supplier<T>) field;
    return (CommandService.Field<V>) this;
  }

  @Override
  public CommandService.Param $(T value) {
    _paramBean.setField(_field, value);
    return this;
  }

  Bean<?> _paramBean;

  Supplier<T> _field;
}
