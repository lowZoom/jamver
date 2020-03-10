package luj.game.server.internal.data.execute.lock;

import com.google.common.collect.ImmutableList;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

final class LoadResultProxy implements InvocationHandler {

  LoadResultProxy(Class<?> resultType, Map<String, Object> resultMap) {
    _resultType = resultType;
    _resultMap = resultMap;
  }

  public Object newInstance() {
    return Proxy.newProxyInstance(_resultType.getClassLoader(), new Class[]{_resultType}, this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    String fieldName = method.getName();
    Object value = _resultMap.get(fieldName);

    if (value == null) {
      return defaultValue(method.getReturnType(), fieldName);
    }
    return value;
  }

  private Object defaultValue(Class<?> fieldType, String fieldName) {
    if (fieldType == List.class) {
      return ImmutableList.of();
    }
    throw new UnsupportedOperationException(fieldName);
  }

  private final Class<?> _resultType;

  private final Map<String, Object> _resultMap;
}
