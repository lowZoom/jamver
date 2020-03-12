package luj.game.server.internal.data.load.meta.field;

import java.util.function.BiConsumer;

public class LoadFieldMeta<R, F> {

  public LoadFieldMeta(Class<F> dataType, BiConsumer<R, F> fieldSetter) {
    _dataType = dataType;
    _fieldSetter = fieldSetter;
  }

  public Class<F> getDataType() {
    return _dataType;
  }

  public BiConsumer<R, F> getFieldSetter() {
    return _fieldSetter;
  }

  private final Class<F> _dataType;

  private final BiConsumer<R, F> _fieldSetter;
}
