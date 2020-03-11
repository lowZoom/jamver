package luj.game.server.internal.data.instance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

@Deprecated
public class DataTempProxy implements InvocationHandler {

  public static final Long GLOBAL = -1L;

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

  public Map<String, Object> getDataMap() {
    return _dataMap;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    if ("toString".equals(method.getName())) {
      return _dataMap.toString();
    }

    String fieldName = method.getName();
    return _dataMap.get(fieldName);
  }

  private Object _instance;

  private final Class<?> _dataType;
  private final Map<String, Object> _dataMap;
}
