package luj.game.server.internal.data.instance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

final class InstanceTempProxy implements InvocationHandler {

  InstanceTempProxy(Class<?> dataType, Map<String, Object> dataMap) {
    _dataType = dataType;
    _dataMap = dataMap;
  }

  public Object newInstance() {
    return Proxy.newProxyInstance(_dataType.getClassLoader(), new Class[]{_dataType}, this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    String fieldName = method.getName();
    return _dataMap.get(fieldName);
  }

  private final Class<?> _dataType;

  private final Map<String, Object> _dataMap;
}
