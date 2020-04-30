package luj.game.server.internal.event.instance;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

public class EventBuilderProxy implements InvocationHandler {

  public EventBuilderProxy(EventInstanceProxy instanceProxy) {
    _instanceProxy = instanceProxy;
  }

  public EventBuilderProxy init(Class<?> eventType) {
    _instance = Proxy.newProxyInstance(eventType.getClassLoader(), new Class[]{eventType}, this);
    return this;
  }

  public void setField(Supplier<?> field, Object value) {
    field.get();
    checkNotNull(_settingField);

    _instanceProxy.setField(_settingField, value);
  }

  public Object getInstance() {
    return _instance;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    String methodName = method.getName();
    if ("toString".equals(methodName)) {
      return toString();
    }
    _settingField = methodName;
    return null;
  }

  @Override
  public String toString() {
    return _instanceProxy.toString();
  }

  private String _settingField;
  private Object _instance;

  private final EventInstanceProxy _instanceProxy;
}
