package luj.game.server.internal.data.execute.load.result.field;

import static com.google.common.base.Preconditions.checkArgument;

import java.lang.reflect.Method;
import java.util.Collection;
import luj.ava.reflect.type.TypeX;
import luj.game.server.internal.data.execute.load.result.ResultFieldProxy;

public enum ResultFieldFactory {
  GET;

  public ResultFieldProxy.Field create(Method fieldMethod) {
    ResultFieldImpl field = new ResultFieldImpl();
    field._name = fieldMethod.getName();
    field._dataType = getDataType(fieldMethod);
    return field;
  }

  private Class<?> getDataType(Method method) {
    Class<?> declaration = method.getReturnType();

    if (declaration == Collection.class) {
      return TypeX.of(method.getGenericReturnType())
          .getTypeParam(0)
          .asClass();
    }

    checkArgument(!Collection.class.isAssignableFrom(declaration),
        "不支持的类型：%s", declaration.getName());

    return declaration;
  }
}
