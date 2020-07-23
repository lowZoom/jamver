package luj.game.server.internal.data.service.param;

import java.util.function.Supplier;
import luj.bean.api.bean.Bean;
import luj.game.server.api.net.GameProtoHandler;

final class ParamImpl<T> implements GameProtoHandler.Data.Param, GameProtoHandler.Data.Field<T> {

  @Override
  public <V> GameProtoHandler.Data.Field<V> set(Supplier<V> field) {
    _field = (Supplier<T>) field;
    return (GameProtoHandler.Data.Field<V>) this;
  }

  @Override
  public GameProtoHandler.Data.Param $(T value) {
    _paramBean.setField(b -> _field, value);
    return this;
  }

  Bean<?> _paramBean;

  Supplier<T> _field;
}
