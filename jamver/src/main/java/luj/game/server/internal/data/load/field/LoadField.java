package luj.game.server.internal.data.load.field;

import java.util.function.BiConsumer;

public class LoadField<R, F> {

  public LoadField(Class<F> dataType, BiConsumer<R, F> fieldSetter) {
    _dataType = dataType;
    _fieldSetter = fieldSetter;
  }

  public void setValue(F value) {
    _value = value;
  }

  public F getValue() {
    return _value;
  }

  public Class<F> getDataType() {
    return _dataType;
  }

  public BiConsumer<R, F> getFieldSetter() {
    return _fieldSetter;
  }

  private F _value;

  private final Class<F> _dataType;

  private final BiConsumer<R, F> _fieldSetter;
}
