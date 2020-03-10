package luj.game.server.internal.data.execute.load;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;

final class ResultFieldProxy implements InvocationHandler {

  ResultFieldProxy(Class<?> resultType) {
    _resultType = resultType;
  }

  public ResultFieldProxy init() {
    _instance = Proxy.newProxyInstance(
        _resultType.getClassLoader(), new Class[]{_resultType}, this);
    return this;
  }

  public Method getField(Function<Object, ?> field) {
    field.apply(_instance);
    return _field;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    _field = method;
    return null;
  }

  private Object _instance;
  private Method _field;

  private final Class<?> _resultType;
}
