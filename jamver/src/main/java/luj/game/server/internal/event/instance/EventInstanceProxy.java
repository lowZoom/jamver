package luj.game.server.internal.event.instance;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

@Deprecated
public class EventInstanceProxy implements InvocationHandler {

  public EventInstanceProxy(Map<String, Object> fieldMap) {
    _fieldMap = fieldMap;
  }

  public EventInstanceProxy init(Class<?> eventType) {
    _instance = Proxy.newProxyInstance(eventType.getClassLoader(), new Class[]{eventType}, this);
    return this;
  }

  public void setField(String name, Object value) {
    _fieldMap.put(name, value);
  }

  public Object getInstance() {
    return checkNotNull(_instance);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    String methodName = method.getName();
    if ("toString".equals(methodName)) {
      return toString();
    }
    return _fieldMap.get(methodName);
  }

  @Override
  public String toString() {
    return _fieldMap.toString();
  }

  private Object _instance;

  private final Map<String, Object> _fieldMap;
}
