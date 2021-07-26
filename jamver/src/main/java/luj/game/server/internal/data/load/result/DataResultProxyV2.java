package luj.game.server.internal.data.load.result;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.EntityFieldGetter;

/**
 * LoadResult里字段对象代理，用于实现setter
 *
 * TODO: 后面会预生成代码，没有生成代码的才用这个动态类作为fallback
 */
public class DataResultProxyV2 implements InvocationHandler {

  public interface FieldHook extends BiConsumer<DataResultProxyV2, String> {
    // NOOP
  }

  public static DataResultProxyV2 create(DataEntity data, Class<?> dataType, FieldHook fieldHook) {
    Map<String, Method> fieldMap = Arrays.stream(dataType.getMethods())
        .collect(Collectors.toMap(Method::getName, Function.identity()));

    DataResultProxyV2 result = new DataResultProxyV2(data, fieldMap, fieldHook);
    result._instance = Proxy.newProxyInstance(
        dataType.getClassLoader(), new Class[]{dataType}, result);

    return result;
  }

  public Object getInstance() {
    return _instance;
  }

  public DataEntity getData() {
    return _data;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    String methodName = method.getName();
    _fieldHook.accept(this, methodName);

    if ("toString".equals(methodName)) {
      return toString();
    }
    return EntityFieldGetter.GET.getOrInitValue(_data, methodName, _fieldMap);
  }

  DataResultProxyV2(DataEntity data, Map<String, Method> fieldMap, FieldHook fieldHook) {
    _data = data;
    _fieldMap = fieldMap;
    _fieldHook = fieldHook;
  }

  private Object _instance;

  private final DataEntity _data;
  private final Map<String, Method> _fieldMap;

  private final FieldHook _fieldHook;
}
