package luj.game.server.internal.data.execute.load.result;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;
import luj.game.server.internal.data.execute.load.result.field.ResultFieldFactory;

/**
 * 用在onLoad里获取要load往的Result字段
 */
public class ResultFieldProxy implements InvocationHandler {

  public interface Field {

    String getName();

    Class<?> getDataType();
  }

  public static ResultFieldProxy create(Class<?> resultType) {
    return new ResultFieldProxy(resultType).init();
  }

  public Field getField(Function<Object, ?> field) {
    // 触发#invoke，获取当前字段
    field.apply(_instance);

    return ResultFieldFactory.GET.create(_field);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    _field = method;
    return null;
  }

  ResultFieldProxy(Class<?> resultType) {
    _resultType = resultType;
  }

  ResultFieldProxy init() {
    _instance = Proxy.newProxyInstance(
        _resultType.getClassLoader(), new Class[]{_resultType}, this);
    return this;
  }

  private Object _instance;
  private Method _field;

  private final Class<?> _resultType;
}
