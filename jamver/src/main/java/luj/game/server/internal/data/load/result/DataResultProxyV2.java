package luj.game.server.internal.data.load.result;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.BiConsumer;
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
    DataResultProxyV2 result = new DataResultProxyV2(data, fieldHook);

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
    return EntityFieldGetter.GET.getValue(_data, methodName);
  }

  DataResultProxyV2(DataEntity data, FieldHook fieldHook) {
    _data = data;
    _fieldHook = fieldHook;
  }

  private Object _instance;

  private final DataEntity _data;
  private final FieldHook _fieldHook;
}
