package luj.game.server.internal.data.instance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

@Deprecated
public class DataTempProxy implements InvocationHandler {

  public static final Long GLOBAL = -1L;
  public static final String ID = "id";

  DataTempProxy(Class<?> dataType, Map<String, Object> dataMap) {
    _dataType = dataType;
    _dataMap = dataMap;
  }

  public DataTempProxy init() {
    _instance = Proxy.newProxyInstance(_dataType.getClassLoader(), new Class[]{_dataType}, this);
    return this;
  }

  public Object getInstance() {
    return _instance;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  public Map<String, Object> getDataMap() {
    return _dataMap;
  }

  public Object invoke(String methodName) {
    if ("toString".equals(methodName)) {
      return toString();
    }
    return _dataMap.get(methodName);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    return invoke(method.getName());
  }

  @Override
  public String toString() {
    return _dataMap.toString();
  }

  private Object _instance;

  private final Class<?> _dataType;
  private final Map<String, Object> _dataMap;
}
