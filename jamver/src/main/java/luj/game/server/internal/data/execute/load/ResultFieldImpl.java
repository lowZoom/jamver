package luj.game.server.internal.data.execute.load;

import java.lang.reflect.Method;
import java.util.Collection;
import luj.ava.reflect.type.TypeX;

final class ResultFieldImpl implements ResultFieldProxy.Field {

  ResultFieldImpl(Method fieldMethod) {
    _fieldMethod = fieldMethod;
  }

  @Override
  public String getName() {
    return _fieldMethod.getName();
  }

  @Override
  public Class<?> getDataType() {
    Class<?> declaration = _fieldMethod.getReturnType();

    if (declaration == Collection.class) {
      return TypeX.of(_fieldMethod.getGenericReturnType())
          .getTypeParam(0)
          .asClass();
    }
    return declaration;
  }

  private final Method _fieldMethod;
}
