package luj.game.server.internal.data.load.result;

import com.google.common.collect.ImmutableList;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * LoadResult根对象代理
 *
 * TODO: 后面会预生成代码，没有生成代码的才用这个动态类作为fallback
 */
public class LoadResultProxy implements InvocationHandler {

  public static LoadResultProxy create(Class<?> resultType) {
    return new LoadResultProxy(resultType, new HashMap<>()).init();
  }

  LoadResultProxy(Class<?> resultType, Map<String, Object> resultMap) {
    _resultType = resultType;
    _resultMap = resultMap;
  }

  public LoadResultProxy init() {
    _instance = Proxy.newProxyInstance(
        _resultType.getClassLoader(), new Class[]{_resultType}, this);
    return this;
  }

  public Object getInstance() {
    return _instance;
  }

  public Map<String, Object> getResultMap() {
    return _resultMap;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    String fieldName = method.getName();
    if ("toString".equals(fieldName)) {
      return _resultMap.toString();
    }

    Object value = _resultMap.get(fieldName);
    if (value == null) {
      return defaultValue(method.getReturnType(), fieldName);
    }
    return value;
  }

  private Object defaultValue(Class<?> fieldType, String fieldName) {
    if (fieldType == Collection.class) {
      return ImmutableList.of();
    }
//    throw new UnsupportedOperationException(fieldName);
    return null;
  }

  private Object _instance;

  private final Class<?> _resultType;
  private final Map<String, Object> _resultMap;
}
